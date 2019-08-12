if ( ! jsLib ) { var jsLib = {}; }
if ( ! jsLib.dom ) { jsLib.dom = {}; }

jsLib.dom.readyList = [];
jsLib.dom.isReady = false;

jsLib.dom.ready = function ( fn ) {
    if ( fn instanceof Function ) {
        if ( jsLib.dom.isReady ) {
            fn.call(document);
        } else {
            jsLib.dom.readyList.push( fn );
        }
    } else {
        if ( jsLib.dom.isReady ) {
            while ( jsLib.dom.readyList.length > 0 ) {
                fn = jsLib.dom.readyList.pop();
                fn.call(document);
            }
        }
    }
}

jsLib.dom.readyInit = function () {
    if ( document.addEventListener ) {   // DOM event model
        // DOM standard DOMContentLoaded event supported
        document.addEventListener(
            "DOMContentLoaded",
            function () {
                jsLib.dom.isReady = true;
                jsLib.dom.ready();
            },
            false
        );
    } else if ( document.attachEvent ) { // IE Event model
        // Are we in an iframe?
        if ( document.documentElement.doScroll && window == window.top ) {
            // No, not in an iframe. Use the doScroll test to
            // simulate DOMContentLoaded by Diego Perini
            // http://javascript.nwbox.com/IEContentLoaded/
            var doScrollPoll = function () {
                if ( jsLib.dom.isReady ) return;
                try {
                    document.documentElement.doScroll("left");
                } catch( error ) {
                    setTimeout( doScrollPoll, 0 );
                    return;
                }
                jsLib.dom.isReady = true;
                jsLib.dom.ready();
            }
            doScrollPoll();
		} else {
            // Yes, we are in an iframe or doScroll isn't supported.
            // Use onreadystatechange
            document.attachEvent(
                "onreadystatechange",
                function () {
                    if ( document.readyState === "complete" ) {
                        jsLib.dom.isReady = true;
                        jsLib.dom.ready();
                    }
                }
            );
        }
        
        // Use window onload in IE as last resort
        if ( window.attachEvent ) {   
            window.attachEvent(
                "onload",
                function () {
                    jsLib.dom.isReady = true;
                    jsLib.dom.ready();
                }
            );
        }
    }
}

jsLib.dom.readyInit();


