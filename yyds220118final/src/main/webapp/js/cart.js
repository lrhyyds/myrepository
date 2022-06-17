var productsnum = new Array();
var productpis = new Array();
var productcheck = new Array();
var productcost = new Array();//操作productcost的位置在本页131行代码
var carids=new Array();//存放已经下单的要删的订单号
(function () {
    window.addEventListener('load', () => {
        //数据


        //我们的用户,登录后,存进了session
        let userInfo = sessionStorage.getItem('User');

        //如果拿到的是空数据(没有用户登录) 那么不处理,并且终止程序
        if (!userInfo) {
            layer.msg('小子,请问你登录了吗?', {icon: 2, time: 1000, shade: 0.4}, function () {
                location.replace('./login.html');
            });
            return false;
        }

        //渲染购物车的数据,以及购物车的功能
        class renderCart {
            constructor(settings = {}) {
                //大盒子
                this.el = document.querySelector(settings.el);
                //渲染数据的区域
                this.ulList = this.el.querySelector('.c-detail ul');
                //全选按钮
                this.allBtn = null;

                this.render();

                /*     //点击按钮 sub ++  / sup --
                     this.subBtn = null;
                     this.supBtn = null;
                     //删除按钮
                     this.delBtn = null;
                     //提交按钮
                     this.submitBnt = this.el.querySelector('.submit');
                     //渲染购物车数据*/
            }

            //渲染数据
            render() {
                //let userId = JSON.parse(userInfo).uno;
                $.ajax({
                    url: `/getMyShoppingCarjq`,
                    type: 'GET',
                }).then(res => {
                    //拿到id,到数据库中去查询,该用户的订单信息
                    let data = res;
                    console.log(res);
                    console.log("jj");
                    console.log(data);
                    /**/
                    let strHTML = `<li>
          <div class="w1">
            <input type="checkbox" class="all">
            <span>全选</span>
          </div>
          <div class="w2">
            商品信息
          </div>
          <div class="w3">
            单价
          </div>
          <div class="w4">
            数量
          </div>
          <div class="w5">
            小计
          </div>
          <div class="w6">
            操作
          </div>
        </li>`;
                    if (!data || !data.length) return;
                    //遍历用户在数据库中的数据
                    let no = 0;
                    /*userpaynum:用户购买该种商品的数量
                    * pprice：商品单价
                    * themoney：支付总金额*/
                    data.forEach(item => {
                        let themoney = item.pprice * item.userpaynum;
                        console.log("cart.js第86行代码订单渲染部分themoney：" + themoney);
                        strHTML += `
            <li data-pid="${item.pid}">
            <span  data-carid="${item.carid}" class="carid" id="datacarid" style="display: none;">${item.carid}</span>
            <div class="w1">
              <input type="checkbox" class="box">
              <span style="display: none">${item.pid - item.pid + no}</span>
            </div>
            <div class="w2">
              <div class="pic">
                <a href="#">
                  <img src="./images/product/${item.pfileName}" alt="">
                </a>
              </div>
              <div class="nameCon">
                <div class="promotionBar">
                  <span>特价抢购中</span>
                  <span>距优惠结束</span>
                  <span>3天3夜</span>
                </div>
                <a href="#">${item.pdesc}</a>
                <span>${item.pdesc}</span>
              </div>
            </div>
            <div class="w3">
              <span>¥${parseFloat(item.pprice).toFixed(2)}</span>
            </div>
            <div class="w4">
              <span class="sup">-</span>
              <!--购买改商品的数量-->
              <input type="text" value="${item.userpaynum}">
              <span class="sub">+</span>
            </div>
            <div class="w5">
              <span class="price" name="${item.pid - item.pid + no}">¥${themoney}</span>
              <span style="display: none">${item.pid - item.pid + no}</span>
            </div>
            <div class="w6">
              <a href="javascript:" class="del">删除</a>
            </div>
          </li>
            `;
                        no++;
                        productsnum.push(1);
                        productpis.push(item.pid);
                        productcheck.push(0);
                        carids.push(item.carid);
                        productcost.push(item.pprice*item.userpaynum);
                        console.log("cart.js第132行代码productpids" + productpis);
                    });
                    //渲染页面
                     /*
                      ullist为ul ,动态加的显示订单数据的li
                     * 将订单显示出来（新增的li没有全选框）
                     * 通过遍历后端响应的数据集合对象
                     * */
                    this.ulList.innerHTML = strHTML;
                    //全选按钮
                    /*all在第60行代码*/
                    this.allBtn = this.el.querySelector('.all');
                    //商品合计
                    /*
                    * zje在cart.html的第208行
                    * */
                    this.total = this.el.querySelector('#zje');
                    //打折
                    //dz在cart.html的第212行
                    this.dz = this.el.querySelector('.dz');
                    //应付金额
                    //yfje在cart.html的第218行
                    this.price = this.el.querySelector('.yfje');
                    /*     //点击按钮 sub ++  / sup --
                             this.subBtn = this.el.querySelector('.sub');
                               this.supBtn = this.el.querySelector('.sup');
                               //删除按钮
                               this.delBtn = this.el.querySelector('.del');*/
                    //处理点击-- ++ 事件
                  //所有订单对象显示完后调用clickHandler（）计算总金额
                    this.clickHandler();

                });
            }

            //点击处理
            clickHandler() {
                let that = this;
                //这里我们使用事件委托,主要是用来实现购物车的 ++ -- 全选 删除 下单 功能
                this.el.addEventListener('click', (event) => {
                    let e = event || window.event;
                    let target = e.target || e.srcElement;
                    //如果不是我们这些功能 我们直接终止
                    if (target.className !== 'sup' && target.className !== 'sub' && target.className !== 'del' && target.className !== 'box' && target.className !== 'all' && target.className !== 'submit') {
                        return false;
                    }

                    //加
                    if (target.className === 'sub') {
                      /*
                      * 1.对应序号订单的总金额
                      * 2.price:显示的所有选中订单（可以是一个，也可以是选中的多个）的总额
                      * */
                      //页面上的数据也要变化

                      /*
                      * previousElementSibling 属性只返回元素节点之前的兄弟元素节点（不包括文本节点、注释节点）；
                      * 商品数量加1
                      * */
                      target.previousElementSibling.value++;
                      //获取到当前订单的总金额
                      let totalPrice = target.parentNode.nextElementSibling.children[0];

                      /*
                      * parentNode：父节点
                      * nextElementSibing:返回指定元素之后的下一个兄弟元素
                      * chilren: 属性返回元素的子元素的集合，是一个 HTMLCollection 对象
                      * */
                      //获取该订单的序号（改变的是几号订单的数据）
                      let thisno = target.parentNode.nextElementSibling.children[1];
                      let thisnonow = thisno.innerText;//thisnonow：当前订单显示时的序号
                      console.log("cart.js第203行代码no：" + thisnonow);
                      //将商品数量设为1
                      productsnum[thisnonow] = productsnum[thisnonow] + 1;
                      console.log("cart.js第206行代码productsnum[thisnonow]"+productsnum[thisnonow]);
                      /*
                      *price:已经是商品数量*单价了=此订单的总金额
                      * 商品数量*订单总金额（此时还未优惠的一个订单的价格）
                      * 如果加了会继续改变总金额（在原来总金额上翻倍增加）
                      * */
                      totalPrice.innerText = `¥${(parseFloat(target.previousElementSibling.value) * parseFloat(target.parentNode.previousElementSibling.children[0].innerText.slice(1))).toFixed(2)}`
                      //获取该订单的总价钱
                      productcost[thisnonow] = totalPrice.innerText.slice(1);
                      console.log("cart.js第215行代码productsnum[thisnonow]"+productcost[thisnonow]);

                      /*
                      * productcost:一个订单的总金额（未打折)
                      *
                      * */

                      //这是所有选中的input 不包括全选按钮 在这里动态计算金额
                      let price = [0];
                      target.parentNode.parentNode.parentNode.querySelectorAll('.box:checked').forEach(item => {
                        price.push(parseFloat(item.parentNode.parentNode.querySelector('.price').innerText.slice(1)));
                      });

                      let count = 0;//选中的总和（累加计算）
                      price.forEach(item => {
                        count += parseFloat(item);
                      });
                      //选中的未打折的总和
                      this.total.innerText = `¥${count.toFixed(2)}`;
                      //选中的打折（优惠部分的金额）
                      this.dz.innerText = `- ${(count.toFixed(2) - count.toFixed(2) * 0.95).toFixed(2)}`;
                      //选中的总额（优惠后的）
                      this.price.innerText = ` ${(count * 0.95).toFixed(2)}`;

                    }
                    //减
                    if (target.className === 'sup') {
                        if (target.nextElementSibling.value <= 1) {
                            layer.msg('大哥,最少购买一件哦!');
                            return false;
                        }

                        target.nextElementSibling.value--;

                        let totalPrice = target.parentNode.nextElementSibling.children[0];
                        totalPrice.innerText = `¥${(parseFloat(target.nextElementSibling.value) * parseFloat(target.parentNode.previousElementSibling.children[0].innerText.slice(1))).toFixed(2)}`

                        /*$.ajax({
                          url: '/api/modify',
                          data: {
                            pCount: parseInt(target.nextElementSibling.value),
                            cId: parseInt(target.parentNode.parentNode.getAttribute('data-cid'))
                          },
                          type: 'POST',
                        }).then(function (res) {
                          layer.msg(res.msg);
                        });*/
                        //这是所有选中的input 不包括全选按钮 在这里动态计算金额
                        let price = [0];
                        target.parentNode.parentNode.parentNode.querySelectorAll('.box:checked').forEach(item => {
                            price.push(parseFloat(item.parentNode.parentNode.querySelector('.price').innerText.slice(1)));
                        })

                        let count = 0;
                        price.forEach(item => {
                            count += parseFloat(item);
                        })
                        //合计
                        this.total.innerText = `¥${count.toFixed(2)}`;
                        //打折
                        this.dz.innerText = `- ${(count.toFixed(2) - count.toFixed(2) * 0.95).toFixed(2)}`;
                        //总额
                        this.price.innerText = ` ${(count * 0.95).toFixed(2)}`;
                    }

                    //删除
                    if (target.className === 'del') {
                        console.log(target.parentNode);
                        console.log(target.parentNode.parentNode.children[0]);
                        layer.confirm('大哥，您确定删除吗', {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            $.ajax({
                                url: '/delCheckedProductsjq',
                                contentType: 'application/json;charset=UTF-8',
                                data: {
                                    /* uno: parseInt(target.parentNode.parentNode.getAttribute('data-cid'))*/
                                    pid: parseInt(target.parentNode.parentNode.getAttribute('data-pid')),
                                    carid: parseInt(target.parentNode.parentNode.children[0].getAttribute('data-carid')),
                                    gg:parseInt(document.getElementById("datacarid").innerHTML)
                                },
                                success: function (count) {
                                    if (count != 0) {
                                        layer.msg("下次一定要买我啊");
                                    } else {
                                        layer.msg("大哥出现了出现了未知错误导致删除失败,还是不要删除我了吧");
                                    }
                                }
                            })/*.then(function (res) {
                layer.msg(res.msg);
                //计算金额
                that.totalPrice();
              });*/
                            target.parentNode.parentNode.remove();
                        }, function () {
                            layer.msg('感谢您不删除我');
                        });

                    }

                    //全选
                    if (target.className === 'all') {
                        //点击全选,拿到li下面的input check全部打钩
                        let listList = this.el.querySelectorAll('li:nth-child(n+2) .box');

                        //全选
                        listList.forEach(item => {
                            item.checked = this.allBtn.checked;
                            console.log("cart.js第319行代码checked：" + item.checked);
                            if (item.checked == true) {//全选
                                for (let i = 0; i < productcheck.length; i++) {
                                    productcheck[i] = 1;
                                }
                            } else {//全不选
                                for (let i = 0; i < productcheck.length; i++) {
                                    productcheck[i] = 0;
                                }
                            }
                            console.log(productcheck)
                        });
                        //计算金额(totalprice方法在本页第467行代码)
                        that.totalPrice();
                        /*
            *this.total: 输出到对应cart.html中的
            *           <span>商品合计:</span>
            *          <span clas="zje" id="zje">¥0.00</span>
            * this.dz：输出到对应cart.html中的
            *         <span>已优惠(95折):</span>
            *          <span class="dz">-¥0.00</span>
            *this.price:输出到对应cart.html中的<span class="yfje">¥ 0.00</span>
            * */
                        if (!target.checked) {
                            //合计
                            this.total.innerText = `¥0`;
                            //打折
                            this.dz.innerText = `- ¥0`;
                            //总额
                            this.price.innerText = `¥0`;
                        }

                    }

                    //单击选中
                    if (target.className === 'box') {
                        /*parentNode.previousElementSibling*/
                        //获取当前订单是否选中
                        console.log("cart.js第349行代码进入单击事件");
                        console.log(target.tagName);
                        console.log("cart.js第351行代码获取该订单的原状态：" + target.nextElementSibling.innerHTML);
                        let thischeckno = target.nextElementSibling.innerHTML;//获取到当前订单渲染时的序号
                        /*为0为未选中状态，1为选中状态，
                        *此时单击选择框，如果原来状态为选中，则单击一次改为未选中，productcheck对应的值设为0
                        * 如果原来状态是未选中，单击一次将改为选中，productcheck对应的值设为1
                        * */
                        if (productcheck[thischeckno] == 0) {
                            productcheck[thischeckno] = 1;
                        } else {
                            productcheck[thischeckno] = 0;
                        }
                        console.log("cart.js第362行代码获取该订单的现状态：" + productcheck[thischeckno]);

                        //拿到所有 商品数据li
                        let length = target.parentNode.parentNode.parentNode.children.length - 1;

                        //这是全选按钮,打钩的条件
                        if (target.parentNode.parentNode.parentNode.querySelectorAll('.box:checked').length === length) {
                            this.allBtn.checked = true;
                        } else {
                            this.allBtn.checked = false;
                        }
                        //这是所有选中的input 不包括全选按钮 在这里动态计算金额
                        let price = [0];
                        //父节点的父节点的父节点，此处对应的元素为：->div->li->ul
                        target.parentNode.parentNode.parentNode.querySelectorAll('.box:checked').forEach(item => {
                            price.push(parseFloat(item.parentNode.parentNode.querySelector('.price').innerText.slice(1)));

                        })
                        let count = 0;
                        price.forEach(item => {
                            count += parseFloat(item);
                        });
                        /*
                        *this.total: 输出到对应cart.html中的
                        *           <span>商品合计:</span>
                        *          <span clas="zje" id="zje">¥0.00</span>
                        * this.dz：输出到对应cart.html中的
                        *         <span>已优惠(95折):</span>
                        *          <span class="dz">-¥0.00</span>
                        *this.price:输出到对应cart.html中的<span class="yfje">¥ 0.00</span>
                        * */
                        //合计
                        this.total.innerText = `¥${count.toFixed(2)}`;
                        //打折
                        this.dz.innerText = `- ${(count.toFixed(2) - count.toFixed(2) * 0.95).toFixed(2)}`;
                        //总额
                        this.price.innerText = ` ${(count * 0.95).toFixed(2)}`;
                        console.log("cart.js第408行代码（单击选中）productcost："+productcost);
                    }

                    //提交
                    if (target.className === 'submit') {
                        //拿到 打钩钩的box  如果>0 那么就证明选中了商品
                        let isSubmit = this.el.querySelectorAll('.box:checked').length > 0
                        if (!isSubmit) {
                            layer.msg('未选中商品?');
                            return false;
                        }
                        layer.confirm('你确定要付款吗,确定口袋里面的钱够吗?', {
                            btn: ['钱够', '没钱了,下个月再下单'] //按钮
                        }, function () {
                            //let pids=parseInt(target.parentNode.parentNode.getAttribute('data-pid'));
                            //var aa=abc;
                            //console.log(aa);
                            //console.log(pids);
                            // layer.msg(pids);
                            console.log("cart.js第427行代码（点击提交订单）productsnum"+productsnum);
                            //判断是否有收货
                            $.ajax({
                                url: "/getUserAddressByuNojq",
                                success: function (address) {
                                    /*没有收货地址 为checkAddress框添加信息并显示*/
                                    if (address == "") {
                                        var html = `<div style='margin-left: 100px; margin-top: 20px'><h1 style='font-size: 18px; margin-left: 6%'>目前您还未添加收获地址!</h1></div>`;
                                        html += `<br/><br/><img src="/images/noaddress.jpg" style="width: 200px; height: 250px; margin-left: 130px"><i class="layui-icon layui-icon-about"></i><br/><br/><br/>`;
                                        html += `<button class="layui-btn" style="width: 120px; height: 30px; margin-left: 33%" onclick="addAddress()">添加收货地址</button>`;
                                        $("#checkAddress").html(html);
                                        layer.open({
                                            title: "收货地址",
                                            type: 1,
                                            content: $("#address"),
                                            area: ['500px', '500px']
                                        });
                                    }
                                    /*有收货地址 为checkAddress框添加信息并显示*/
                                    else {
                                        var html = "";
                                        console.log(address);
                                        /*将地址信息添加到checkAddress框中*/
                                        for (let i = 0; i < address.length; i++) {
                                            var ureceivingPhone = address[i].ureceivingPhone;
                                            var uAddress = address[i].uaddress;
                                            var consign = address[i].uconsignee;
                                            var addressAll = uAddress + "," + consign + "," + ureceivingPhone;
                                            html += `<div style="margin-left: 40px; margin-top: 20px; font-size: 13px;"><input  style="font-size: 16px;" type="radio" checked name="addressInfo" value="`;
                                            html += addressAll;
                                            html += `">地址:`;
                                            html += uAddress;
                                            html += `&nbsp;&nbsp;/&nbsp;&nbsp;收货人:`;
                                            html += consign;
                                            html += `&nbsp;&nbsp;/&nbsp;&nbsp;联系电话:`;
                                            html += ureceivingPhone;
                                            html += `</div>`;
                                        }
                                        /*添加确认按钮*/
                                        html += `<div style='margin-left: 140px; margin-top: 20px;'><input class='layui-btn' type='button' value='确定' onclick="thispayment()"></div>`;
                                        $("#checkAddress").html(html);
                                        /*打开id为address的div*/
                                        layer.open({
                                            title: "选择地址",
                                            type: 1,
                                            area: ["500px", "500px"],
                                            content: $("#address"),
                                        })
                                    }
                                }
                            })
                            layer.msg('恭喜老板,假装你购买成功了!');
                        }, function () {
                            layer.msg('努力打工...下个月再买');
                        });
                    }
                });
            }

            //计算总金额（在本页332行调用）全选顶单后
            totalPrice() {
                /* li:nth-child(n+2)：获取到第二个及以后的li标签元素
                * li:nth-child(n+2) .price 获取到第二个及以后的li标签元素的price类标签
                * 获取的值为:themoney--应付总金额
               */
                //全选,拿到总金额
                let totalPrice = this.el.querySelectorAll('li:nth-child(n+2) .price');
                //layer.msg(totalPrice[1]);
                console.log(totalPrice[1])

                //把每个订单的总额转换成数组存放
                let count = [];
                let m=1;
              totalPrice.forEach(item => {
                /*
                *slice():
                * 一个参数：slice()方法返回从该参数指定位置开始到当前数组末尾的所有项
                * 两个参数：该方法返回起始和结束位置之间的项（但不包括结束位置的项）。
                *slice不会影响原始数组。
                * toFixed():
                * toFixed()方法可把 Number 四舍五入为指定小数位数的数字。
                * innerText:获取到标签的内容（应付总金额），每个订单的总金额
                * */
                console.log("cart.js第510行代码（全选计算总结额）totalPrice"+m+":"+parseFloat(item.innerText.slice(1)).toFixed(2));
                count.push(parseFloat(item.innerText.slice(1)).toFixed(2));
                ++m;
              })

                //统计出全部订单的总金额
                let price = 0;//此时对应前端界面应付总额
                count.forEach(item => {
                    price += parseFloat(item);
                })
                /*
                * allBtn：全选按扭
                * total:商品合计总价
                * dz:优惠部分的金额
                * price:优惠后的应付金额
                * */
                /*打折*/
                //合计（未打折前的总额）price：全部订单的总额
                this.total.innerText = `¥${price.toFixed(2)}`;
                //打折（优惠部分的金额）
                this.dz.innerText = `- ${(price.toFixed(2) - price.toFixed(2) * 0.95).toFixed(2)}`;
                //总额（优惠后的金额）
                this.price.innerText = ` ${(price * 0.95).toFixed(2)}`;
            }

        }

        //实例化
        new renderCart({
            el: '.m-cart'
        });

    });
})();

//支付方法
function thispayment() {
    console.log("cart.js第547行代码（支付方法）进入payment方法");
    console.log("cart.js第548行代码（支付方法）productsnum：" + productsnum);/*
   var $contentinfo= $("#contentinfo"); //jq对象转换为dom对象
    $contentinfo[0].querySelectorAll('.box:checked').forEach(item => {
        carids.push(parseFloat(item.parentNode.parentNode.querySelector('.carid').innerText));
    })*/
    console.log("cart.js第553行代码（支付方法）carids："+carids);
    var addressInfoAll = ($("input[name='addressInfo']").val());
    $.post("/paymentjq", {
        "addressInfoAll": addressInfoAll,
        'productnum': productsnum,
        'productpis': productpis,
        'productcheck': productcheck,
        'productcost': productcost,
        'carids':carids
    }, function (res) {
        if (res == 1) {
            layer.msg("购买成功!", {time: 1000}, function () {
                location.reload();
            });
        } else {
            layer.alert("钱不够啊!", {time: 1500}, function () {
                location.reload();
            });
        }
    });
}