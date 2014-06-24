<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>�ɼ����� </title>
        <!--[if IE 6]>
            <script type="text/javascript" src="http://dev.baidu.com/wiki/static/map/tuan/js/DD_belatedPNG_0.0.8a-min.js"></script>
            <script>DD_belatedPNG.fix("#float_search_bar");</script>
       <![endif]-->
       <style>
            #preview{
                border: 1px solid #bfd2e1;
                width: 490px;
                height: 368px;
                font-family: Arial, Helvetica, sans-serif,"����";
            }
            #map_container{
                height: 368px;
            }
            #float_search_bar{
                z-index: 2012;
                position: absolute;
                width: 480px;
                height: 31px;
                background: url("http://dev.baidu.com/wiki/static/map/tuan/images/search_bar.png") repeat-x;
                background-position: 0 -21px;
                padding: 3px 0 0 10px;
            }
            #float_search_bar label, #float_search_bar span{
                color: #0787cb;
                font-size: 14px;
            }
            #float_search_bar input{
                width: 180px;
                height: 16px;
                margin-top: 1px;
            }
            #float_search_bar input:focus{
                outline: none;
            }
            #float_search_bar button{
                border: 0;
                color: white;
                width: 77px;
                height: 20px;
                background: url("http://dev.baidu.com/wiki/static/map/tuan/images/search_bar.png") no-repeat;
                background-position: 0 0;
                margin-right: 5px;
                cursor: pointer;
            }
        </style>
   </head>
   
   <body>
   <div id="preview">
        <div id="float_search_bar">
            <label>����</label>
            <input type="text" id="keyword" />
            <button id="search_button">����</button>
            <span>�����ͼ���ע��ȡ����</span>
        </div>
        <div id="map_container"></div>
    </div>
    <div id="result" style="margin-top: 4px;"></div>
    
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
    <script type="text/javascript">
        function getUrlParas(){
            var hash = location.hash,
                para = {},
                tParas = hash.substr(1).split("&");
            for(var p in tParas){
                if(tParas.hasOwnProperty(p)){
                    var obj = tParas[p].split("=");
                    para[obj[0]] = obj[1];
                }
            }
            return para;
        }
        var para = getUrlParas(),
            center = para.address?decodeURIComponent(para.address) : "�ٶȴ���",
            city = para.city?decodeURIComponent(para.city) : "������";
    
        document.getElementById("keyword").value = center;
    
        var marker_trick = false;
        var map = new BMap.Map("map_container");
        map.enableScrollWheelZoom();
    
        var marker = new BMap.Marker(new BMap.Point(116.404, 39.915), {
            enableMassClear: false,
            raiseOnDrag: true
		 });
        marker.enableDragging();
        map.addOverlay(marker);
    
        map.addEventListener("click", function(e){
            if(!(e.overlay)){
                map.clearOverlays();
                marker.show();
                marker.setPosition(e.point);
                setResult(e.point.lng, e.point.lat);
            }
        });
        marker.addEventListener("dragend", function(e){
            setResult(e.point.lng, e.point.lat);
        });
    
        var local = new BMap.LocalSearch(map, {
            renderOptions:{map: map},
			 pageCapacity: 1
        });
        local.setSearchCompleteCallback(function(results){
            if(local.getStatus() !== BMAP_STATUS_SUCCESS){
                alert("�޽��");
            } else {
			     marker.hide();
			 }
        });
        local.setMarkersSetCallback(function(pois){
            for(var i=pois.length; i--; ){
                var marker = pois[i].marker;
                marker.addEventListener("click", function(e){
                    marker_trick = true;
                    var pos = this.getPosition();
                    setResult(pos.lng, pos.lat);
                });
            }
        });
    
        window.onload = function(){
            local.search(center);
            document.getElementById("search_button").onclick = function(){
                local.search(document.getElementById("keyword").value);
            };
            document.getElementById("keyword").onkeyup = function(e){
                var me = this;
                e = e || window.event;
                var keycode = e.keyCode;
                if(keycode === 13){
                    local.search(document.getElementById("keyword").value);
                }
            };
        };
        function a(){
            document.getElementById("float_search_bar").style.display = "none";
        }
        
        /*
         * setResult : ����õ���ע��γ�Ⱥ�Ĳ���
         * ���޸Ĵ˺�����������������
         * lng: ��ע�ľ���
         * lat: ��ע��γ��
         */
        function setResult(lng, lat){
            document.getElementById("result").innerHTML = lng + ", " + lat;
        }
   </script>
    </body>
</html>