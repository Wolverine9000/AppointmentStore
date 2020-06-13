<%-- 
    Document   : findAdmin
    Created on : May 21, 2012, 8:35:22 AM
    Author     : williamdobbs
--%>

<div id="adminMenu" class="alignLeft">
    <p><a href="<c:url value='viewCustomers'/>">view all customers</a></p>

    <p><a href="<c:url value='viewOrders'/>">view all orders</a></p>

    <p><a href="<c:url value='unProcessedOrders'/>">un-processed orders</a></p>

    <p><a href="<c:url value='findAdmin'/>">search for orders</a></p>

    <p><a href="<c:url value='processedOrders'/>">processed</a></p>

    <p><a href="<c:url value='writeAssociatePicture'/>">write picture</a></p>

    <p><a href="<c:url value='logout'/>">log out</a></p>
</div>
<table id="adminTable" class="detailsTable">
    <script type="text/javascript" charset="utf-8">

        $(function() {
            var dates = $( "#from, #to" ).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 2,
                minDate: -25, maxDate: "+1M +20D",
                onSelect: function( selectedDate ) {
                    var option = this.id == "from" ? "minDate" : "maxDate",
                    instance = $( this ).data( "datepicker" ),
                    date = $.datepicker.parseDate(
                    instance.settings.dateFormat ||
                        $.datepicker._defaults.dateFormat,
                    selectedDate, instance.settings );
                    dates.not( this ).datepicker( "option", option, date );
                }
            });
        });
    </script>   

    <div class="demo">
        <form action='invoiceDateRange' method="post">
            <label for="from">From</label>
            <input type="text" id="from" name="from"/>
            <label for="to">to</label>
            <input type="text" id="to" name="to"/>
            <INPUT type="submit" name="Submit" value="search dates">
        </form>
    </div><!-- End demo -->
</table>
<FORM ENCTYPE="multipart/form-data" ACTION=
      "upload.jsp" METHOD=POST>
    <br><br><br>
    <center>
        <table border="0" bgcolor=#ccFDDEE>
            <tr>
            <center>
                <td colspan="2" align="center"><B>UPLOAD THE FILE</B><center></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">&nbsp;</td>
                    </tr>
                    <tr>
                        <td><b>Choose the file To Upload:</b></td>
                        <td><INPUT NAME="file" TYPE="file"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="Send File"> </td>
                    </tr>
                    <table>
                </center> 
                </FORM>