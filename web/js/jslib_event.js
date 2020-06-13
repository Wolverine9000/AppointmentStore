if ( ! jsLib ) {
    var jsLib = {};
}

if ( ! jsLib.event ) {
    jsLib.event = {};
}

jsLib.event.handlerId = 1;
    
jsLib.event.add = function (element, type, handler) {
    if (!handler.handlerId) {
        handler.handlerId = this.handlerId++;
    }
    
    if ( element.addEventListener ) {
        // DOM Standard
        var oldID = "jsLib_old_" + type + handler.handlerId;
        var newID = "jsLib_new_" + type + handler.handlerId;
        element[oldID] = handler;
        element[newID] = function(evt) {
            element[oldID](jsLib.event.fixEvent(evt));
        }
        element.addEventListener( type, element[newID], false);
        return true;
    } else if ( element.attachEvent ) {
        // IE
        var oldID = "jsLib_old_" + type + handler.handlerId;
        var newID = "jsLib_new_" + type + handler.handlerId;
        element[oldID] = handler;
        element[newID] = function() {
            element[oldID](jsLib.event.fixEvent(window.event));
        }
        element.attachEvent("on"+type, element[newID]);
        return true;
    }
    return false;
}
    
jsLib.event.remove = function (element, type, handler) {
    if (element.removeEventListener) {
        // DOM Standard
        var oldID = "jsLib_old_" + type + handler.handlerId;
        var newID = "jsLib_new_" + type + handler.handlerId;
        element.removeEventListener(type, element[newID], false);
        element[newID] = null;
        element[oldID] = null;
        return true;
    } else if ( element.detachEvent ) {
        // IE
        var oldID = "jsLib_old_" + type + handler.handlerId;
        var newID = "jsLib_new_" + type + handler.handlerId;
        element.detachEvent( "on"+type, element[newID] );
        element[newID] = null;
        element[oldID] = null;
        return true;
    }
    return false;
}
    
jsLib.event.fixEvent = function (oEvt) {
    if ( oEvt.fixed === true ) return oEvt;
    var evt = {};
    evt.oEvt = oEvt;
    
    // Event properties
    evt.type = oEvt.type;
    evt.target = oEvt.target || oEvt.srcElement || document;
    if ( evt.target.nodeType == 3 ) {
        evt.target = evt.target.parentNode;
    }
    evt.timeStamp = oEvt.timeStamp || (new Date()).valueOf();
    
    // Event methods
    evt.preventDefault = function () {
        if ( oEvt.preventDefault ) {
            oEvt.preventDefault();
        } else {
            oEvt.returnValue = false;
        }
    }
    
    evt.stopPropagation = function () {
        if ( oEvt.stopPropagation ) {
            oEvt.stopPropagation();
        } else {
            oEvt.cancelBubble = true;
        }
    }
    
    // Fix mouse event properties
    if ( jsLib.event.fixMouse ) {
        jsLib.event.fixMouse( oEvt, evt );
    }
    
    // Fix keyboard event properties
    if ( jsLib.event.fixKeys ) {
        jsLib.event.fixKeys( oEvt, evt );
    }
    
    return evt;
}
    
