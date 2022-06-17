(function () {


    window.addEventListener('load', () => {
        //获取自身到body的距离函数
        function offsetDis(obj) {
            let left = 0;
            let top = 0;

            while (obj) {
                left += obj.offsetLeft;
                top += obj.offsetTop;
                obj = obj.offsetParent;
            }
            return {left, top};
        }

        //用来装图片数据....
        let dataImgs = {};

        class renderData {
            constructor(settings = {}) {
                //这个是内容区域
                //对应要渲染的数据,以及数据库中的字段
                this.el = document.querySelector(settings.el);
                //分别拿到数据存放的标签 name, text_l, text_f, tag, tag_time, priceNew, priceOld, fz, cx, gwf, xz, yf, ps, fw, gg, count, zoomImgs
                this.name = this.el.querySelector('.intro h2');//商品名
                this.text_l = this.el.querySelector('.intro .text span');//商品简介
                this.tag = this.el.querySelector('.m-limitedPrice .content');//特价
                /*
          * span:nth-of-type(2):选择同一父元素下的第二个span
          * */
                this.priceNew = this.el.querySelector('.price span:nth-of-type(2)');//.price下第二个span：售价
                this.priceOld = this.el.querySelector('.price span:nth-of-type(3)');//.price下第三个span:原件
                this.cx = this.el.querySelector('.price .sale span:nth-of-type(2)');//促销
                this.ps = this.el.querySelector('.price .delivery span:nth-of-type(2)');//配送
                this.gg = this.el.querySelector('.specProp ul');//规格
                this.count = this.el.querySelector('.u-selnum input');//数量

                this.text_f = this.el.querySelector('.intro .text a');
                this.tag_time = this.el.querySelector('.m-limitedPrice .countdown');
                this.fz = this.el.querySelector('.price .canClick span:nth-of-type(2)');
                this.gwf = this.el.querySelector('.price .m-feedbackBonus span:nth-of-type(2)');
                this.xz = this.el.querySelector('.price .pointInfo span:nth-of-type(2)');
                this.yf = this.el.querySelector('.price .freightText a');
                this.fw = this.el.querySelector('.price .policyBox a');
                //渲染数据上去
                this.render();


                //点击事件增加
                this.click = this.el.querySelector('.u-selnum');
                this.addClick();
                //数据提交
                this.submit = this.el.querySelector('.btn');
                this.submitClick();
            }

            //数据的渲染
            render() {

                //没有传id,不让查看这个页面,如果直接访问订单页面,不让访问
                if (!(location.search)) {
                    this.el.innerHTML = "";
                    layer.msg('找不到这个商品的信息', {icon: 2, time: 2000, shade: 0.4}, function () {
                        location.replace('index.html');
                    });
                    return false;
                }

                //从数据库中获取商品信息
                let pId = location.search.split('?')[1].split("=")[1];
                console.log(pId);
                $.ajax({
                    url: `/getProductsByPid?pid=${pId}`,
                    type: 'GET',
                }).then(res => {

                    // let { data, code } = res;
                    let data = res;
                    let code = res;
                    console.log(code);
                    console.log(data);
                    let {pname, pprice, pdesc, pfileName} = data;

                    /*       let { name, text_l, text_f, tag, tag_time, priceNew, priceOld, fz, cx, gwf, xz, yf, ps, fw, pfileName, count, zoomImgs } = data;

                           //判断是否存在
                           if (!(code === 200 && data)) {
                             layer.msg('你在瞎搞,我找不到这个商品的信息', { icon: 2, time: 2000, shade: 0.4 }, function () {
                               location.replace('login.html');
                             });
                             return false;
                           }
                 */
                    //拿到所有的数据和标签,进行渲染
                    this.name.innerText = pname;
                    this.text_l.innerText = pdesc;
                    this.priceNew.innerText = pprice;
                    this.priceOld.innerText = pprice * 1.5;

                    //this.text_l.innerText = text_l;
                    //查看评价
                    //this.text_f.innerHTML = text_f + '<i class="iconfont">&#xe621;</i>';
                    //特价
                    //this.tag.innerText = tag;
                    //特价剩余时间
                    //this.tag_time.innerText = tag_time;
                    //this.priceNew.innerText = priceNew;
                    //this.priceOld.innerText = priceOld;
                    //优惠
                    //this.fz.innerText = fz;
                    //this.cx.innerText = cx;
                    //this.gwf.innerText = gwf;
                    //this.xz.innerText = xz;
                    //this.yf.innerText = yf;
                    //this.ps.innerText = ps;
                    /*this.fw.innerHTML = `
                      <span>${fw[0]}  </span>
                      <span>${fw[1]} > </span>
                      <span>${fw[2]} > </span>
                      <span>${fw[3]}  </span>
                    `;*/
                    this.gg.innerHTML = `
            <li>
              <img src="" alt="" title="${pfileName}" style="width=100%;height=100%">
            </li>
            <li>
            <img src="" alt="" title="${pfileName}" style="width=100%;height=100%">
            </li>
          `;

                    //放大镜需要的图片
                    dataImgs = pfileName;
                    new Zoom({
                        el: '#zoom'
                    });
                })
            }

            //增加减少
            addClick() {
                this.click.addEventListener('click', (event) => {
                    let e = event || window.event;
                    let target = e.target || e.srcElement;

                    if (target.nodeName !== 'SPAN') {
                        return false;
                    }
                    // sub是-  sup 是+
                    if (target.className === 'sub') {
                        if (this.click.children[1].value <= 1) {
                            layer.msg('最少购买一件');
                            return false;
                        }
                        this.click.children[1].value--;
                    }

                    if (target.className === 'sup') {
                        this.click.children[1].value++;
                    }

                })
            }

            //购买,加入购物车
            submitClick() {
                this.submit.addEventListener('click', (event) => {
                    let e = event || window.event;
                    let target = e.target || e.srcElement

                    //如果不是点击的加入购物车,或者立即购买按钮
                    if (target.className !== 'submit' && target.className !== 'addCart') {
                        return false;
                    }

                    //拿到sessionStorage里面的当前登录用户
                    let userInfo = JSON.parse(sessionStorage.getItem('User'));
                    if (!userInfo) {
                        layer.msg('请先登录?');
                        return false;
                    }

                    //拿到用户id
                    let userId = userInfo.id;
                    console.log("购物的userrid：" + userId);

                    let pId = parseInt(location.search.split("=")[1]);
                    let pCount = parseInt(this.count.value);
                    //封装成一个对象,发送给接口
                    /* let obj = {
                       //产品id,用户id,随机小图中的一张图片,商品名称,商品优惠,优惠结束时间,产品规格,价格,数量
                       pId: parseInt(location.search.split("=")[1]),
                       //uId: parseInt(userId),
                       //img: dataImgs.smallImgs,
                       pName: this.name.innerText,
                       //pYh: this.tag_time.innerText.slice(5, this.tag_time.innerText.length),
                       //pGg: '规格暂时固定死,无法选择',
                       //pPrice: parseFloat(this.priceNew.innerText.slice(1, this.priceNew.innerText.length)),
                       //pCount: parseInt(this.count.value)
                     }*/
                    // 这里是随机一张图片
                    // img:  dataImgs.smallImgs[parseInt(Math.random() * 10) % dataImgs.smallImgs.length],

                    console.log("pid:" + pId + " pcount" + pCount);

                    //发送给后端
                    $.ajax({
                        url: '/addShoppingCar',
                        data: {"pid": pId, "pcount": pCount},
                        type: 'post'
                    }).done(function (res) {
                        if (res != 0) {
                            layer.msg('添加到购物车', {icon: 1, time: 1000, shade: 0.4});
                        } else {
                            layer.msg('出现未知错误，添加失败', {icon: 2, time: 1000, shade: 0.4});
                        }

                    });

                    //如果点击的是立即购买直接跳转到购买页面
                    if (target.className == 'submit') {
                        location.href = './cart.html'
                    }

                });
            }
        }

        /*
        * detailHD绑定的组件在detail.html第138行代码
        * */
        new renderData({
            el: '.detailHD'
        });

        //放大镜的实现
        class Zoom {
            constructor(settings = {}) {
                //整个放大镜
                this.el = document.querySelector(settings.el);

                //放大镜大图
                this.bigImg = this.el.children[0];
                //放大镜中图
                this.midImg = this.el.children[1];
                //放大镜mask 遮罩层
                this.midMask = this.midImg.children[1];
                //小图片list列表
                this.samllImg = this.el.children[2];

                //图片数据
                this.data = dataImgs;

                //动态加载 小图list,大图,中图,并且默认显示第一张.
                this.render();
                //鼠标在mid中移动
                this.mouseHanlder();
                //点击小图,切换中图大图
                this.clickHandler();
            }

            //动态加载图片到网页,中图和大图默认显示第一张.
            render() {
                //let { bigImgs, middleImgs, smallImgs } = dataImgs;
                let bigImgs = dataImgs;
                let middleImgs = dataImgs;
                let smallImags = dataImgs;
                let strHtml = `<li style="border: 1px solid black"><img src="/images/product/${smallImags}" style="width: 100px; height: 90px" alt="" data-index="0"></li>`;
                strHtml += `<li style="border: 1px solid black"><img src="/images/product/${smallImags}" style="width: 100px; height: 90px" alt="" data-index="1"></li>`;
                strHtml += `<li style="border: 1px solid black"><img src="/images/product/${smallImags}" style="width: 100px; height: 90px" alt="" data-index="2"></li>`;
                strHtml += `<li style="border: 1px solid black"><img src="/images/product/${smallImags}" style="width: 100px; height: 90px" alt="" data-index="3"></li>`;
                //strHtml += `<li><img src="/images/product/${smallImags}" style="width: 100px; height: 90px" alt="" data-index="4"></li>`;
                /* smallImgs.forEach((item, index) => {
                   strHtml += `<li><img src="${item}" alt="" data-index="${index}"></li>`
                 })*/
                //把小图添加到ul标签中
                this.samllImg.children[0].innerHTML = strHtml;
                //显示第一张中图和第一张大图
                this.midImg.children[0].src = "/images/product/" + middleImgs;
                this.bigImg.children[0].src = "/images/product/" + bigImgs;
            }

            //放大镜具体实现
            mouseHanlder() {
                //光标移入midImg
                this.midImg.addEventListener('mouseenter', () => {
                    //让mask, 和大图 显示
                    this.midMask.style.display = 'block';
                    this.bigImg.style.display = 'block';

                    //光标在midImg中移动
                    this.midImg.addEventListener('mousemove', (event) => {
                        let e = event || window.event;
                        //改变遮罩层的大小,让遮罩层也要成比例关系
                        //公式
                        //模态框W = (中图W/大图W)*大区域W  W代表宽度
                        //模态框H = (中图H/大图H)*大区域H  H代表高度
                        //这一步可以提前计算好,然后在css中固定大小

                        //公式: 鼠标距离盒子左边距离 =  页面到鼠标的距离 - 盒子距离页面左边的距离
                        let msX = e.pageX - offsetDis(this.midImg).left - this.midMask.offsetWidth / 2;
                        let msY = e.pageY - offsetDis(this.midImg).top - this.midMask.offsetHeight / 2;

                        //不能让mask超出
                        if (msX < 0) {
                            msX = 0;
                        } else if (msX >= this.midImg.offsetWidth - this.midMask.offsetWidth) {
                            msX = this.midImg.offsetWidth - this.midMask.offsetWidth;
                        }

                        if (msY < 0) {
                            msY = 0;
                        } else if (msY >= this.midImg.offsetHeight - this.midMask.offsetHeight) {
                            msY = this.midImg.offsetHeight - this.midMask.offsetHeight;
                        }

                        //mask跟随光标移动
                        this.midMask.style.left = msX + 'px';
                        this.midMask.style.top = msY + 'px';

                        //大图进行移动
                        //移动的比例公式
                        // scaleW = 大图W / 中图W W代表宽度
                        // scaleH = 大图H / 中图H H代表高度
                        let scaleW = this.bigImg.children[0].offsetWidth / this.midImg.children[0].offsetWidth;
                        let scaleH = this.bigImg.children[0].offsetHeight / this.midImg.children[0].offsetHeight;

                        this.bigImg.children[0].style.left = -scaleW * msX + 'px';
                        this.bigImg.children[0].style.top = -scaleH * msY + 'px';

                        //光标离开midImg
                        this.midImg.addEventListener('mouseleave', () => {
                            //让mask, 和大图 隐藏
                            this.midMask.style.display = 'none';
                            this.bigImg.style.display = 'none';
                        });
                    });
                });
            }

            //点击换图
            clickHandler() {
                let {bigImgs, middleImgs, smallImgs} = this.data;

                this.samllImg.addEventListener('click', (event) => {
                    let e = event || window.event;
                    let target = e.target || e.srcElement;

                    if (target.nodeName !== 'IMG') {
                        return false;
                    }
                    let indexTemp = target.getAttribute('data-index');

                    Array.prototype.slice.call(this.samllImg.children[0].children).forEach((item, index) => {
                        item.style.border = index == indexTemp ? '1px solid red' : 'none';
                        this.midImg.children[0].src = middleImgs[indexTemp];
                        this.bigImg.children[0].src = bigImgs[indexTemp];
                    });
                });
            }
        }

    })
})();