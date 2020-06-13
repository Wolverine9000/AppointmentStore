<%-- 
    Document   : progressbar
    Created on : May 4, 2013, 4:03:37 PM
    Author     : williamdobbs
--%>

<script src="js/jquery.ui.progressbar.js" type="text/javascript"></script>

<script>
    $(document).ready(function() {
        $("#progressbar").progressbar({
            value: false
        });
    });
</script>

<div id="progressbar" style="width: 300px"></div>

