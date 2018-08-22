/*---动态加载js,css文件插件---*/
/*
 * <%-- loadLibrary初始化，获取跟目录 --%>
 * <script type="text/javascript">
		$(function(){
			loadLibrary.init({
				nodeName:"SCRIPT",
				src:"${pageContext.request.contextPath}/"
			})
		});
		//调用
		loadLibrary("layer.js,jquery")
	</script>
 */
(function() {
    var isLocal, baseUrl, config;
    /*--------main function--------*/
    window.loadLibrary = function(names, async) {
        if (typeof names == "string" && names.length) {
            names = names.split(",");
            for (var i in names) {
                var lib = config[names[i]];
                if (lib) {
                    if (lib.type == "script") {
                        var node = document.createElement("script");
                        node.src = !isLocal && lib.remote ? lib.remote : baseUrl + lib.local;
                    } else if (lib.type == "stylesheet") {
                        var node = document.createElement("link");
                        node.rel = "stylesheet";
                        node.href = !isLocal && lib.remote ? lib.remote : baseUrl + lib.local;
                    }
                    document.readyState != "complete" && !async ? document.write(node.outerHTML) : (document.body || document.head || document.documentElement).appendChild(node);
                }
            }
        }
    };
    /*--------init function--------*/
    loadLibrary.init = function(context) {
        if (context.nodeName && context.nodeName == "SCRIPT" && context.src && context.src.length) {
            this.isLocal = isLocal = !!location.href.match(/^(file:\/\/)|^(http(s)?:\/\/localhost)|^(http:\/\/127.0.0.1)/);
            this.baseUrl = baseUrl = context.src.substring(0, context.src.lastIndexOf("/")) + "/";
            this.config = config;
        }
        return loadLibrary;
    };
    /*--------config object--------*/
    config = {
//		"jquery": {
//	        type: "script",
//	        local: "jquery-1.9.1.min.js",
//	        remote: "http://apps.bdimg.com/libs/jquery/1.9.1/jquery.min.js"
//	    },
        "layer.js": {
        	type: "script",
        	local: "static/plugins/layer/layer.js",
        	remote: ""
        }
    };
})();