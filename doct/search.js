let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '账户接口',
    link: '账户接口',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '按照身份证查询账户',
});
api[0].list.push({
    order: '2',
    desc: '新增账户',
});
api[0].list.push({
    order: '3',
    desc: '存款',
});
api.push({
    alias: 'baseController',
    order: '2',
    desc: '基础接口',
    link: '基础接口',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取当前时间',
});
api[1].list.push({
    order: '2',
    desc: '增加一天',
});
api.push({
    alias: 'ClientProductController',
    order: '3',
    desc: '客户-产品接口',
    link: '客户-产品接口',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '查看用户购买的理财产品',
});
api.push({
    alias: 'FinanceProductController',
    order: '4',
    desc: '理财产品',
    link: '理财产品',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查看理财产品列表',
});
api[3].list.push({
    order: '2',
    desc: '根据用户id查看可购买的产品列表',
});
api[3].list.push({
    order: '3',
    desc: '用户购买理财产品',
});
api.push({
    alias: 'LoanRecordController',
    order: '5',
    desc: '贷款记录',
    link: '贷款记录',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '按照用户id查询对应的贷款记录',
});
api[4].list.push({
    order: '2',
    desc: '按照贷款id查询对应的贷款记录',
});
api[4].list.push({
    order: '3',
    desc: '清缴罚款',
});
api[4].list.push({
    order: '4',
    desc: '全部还款',
});
api[4].list.push({
    order: '5',
    desc: '部分还款',
});
api[4].list.push({
    order: '6',
    desc: '更新新的一天',
});
api.push({
    alias: 'WaterLogController',
    order: '6',
    desc: '流水',
    link: '流水',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询账户流水',
});
api[5].list.push({
    order: '2',
    desc: '查询账户流水',
});
api.push({
    alias: 'dict',
    order: '7',
    desc: '数据字典',
    link: 'dict_list',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}