/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var sortBy;
var svcObj1;
var isAllSelected = false;

// settings tabs
$(function () {
    $("#services-tabs").tabs();
});

$("#serviceform").validate({
    rules: {
        description: {
            required: true,
            minlength: 3
        }
    }
});

$(document).ready(function () {
    serviceListing = serviceList.getServiceList();
    renderServiceList(serviceListing);
    var serviceInfo = $("#service-info");

    $(document).on("click", "#services #services-list-table tbody tr td:nth-child(-n+6)", this, function (evt) {
        var service = $("#serviceObj", this).val();
        var svcObj = $.parseJSON(service);

        var svcInfoTable = servicesClass.renderSvcInfo(svcObj);

        openMessage(svcObj);
        serviceInfo.dialog("option", "title", "Service Info");
        serviceInfo.dialog("open");
        serviceInfo.dialog("option", "minHeight", 350);
        serviceInfo.dialog("option", "Height", 480);
        serviceInfo.dialog("option", "width", 450);
        serviceInfo.dialog("option", "minWidth", 400);
        serviceInfo.dialog("option", "maxWidth", 700);
        $("#service-info-table tbody").html(svcInfoTable);
    });
});

var renderServiceList = function (serviceArr, sortBy) {
    var servicesTable = $("#services-list-table tbody");
    servicesTable.html(""); // clear element table

    switch (sortBy)
    {
        case "id":
            serviceArr = sortServices.byID(serviceArr);
            break;
        case "name":
            serviceArr = sortServices.byName(serviceArr);
            break;
        case "description":
            serviceArr = sortServices.byDescription(serviceArr);
            break;
        case "category":
            serviceArr = sortServices.byCategory(serviceArr);
            break;
        case "serviceTime":
            serviceArr = sortServices.byServiceTime(serviceArr);
            break;
        case "price":
            serviceArr = sortServices.byPrice(serviceArr);
            break;
        default:
            serviceArr = sortServices.byName(serviceArr);
            break;
    }

    for (var i = 0; i < serviceArr.length; i++)
    {
        var price = parseFloat(serviceArr[i].price);
        var serviceTime = minutesFormat(serviceArr[i].serviceTime);

        servicesTable.append(
                "<tr id='" + serviceArr[i].serviceId + "'>"
                + "<td>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + serviceArr[i].serviceId + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "service-name'>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + serviceArr[i].name + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "description'>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + serviceArr[i].description + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "category'>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + serviceArr[i].category + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "serviceTime'>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + serviceTime + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "price'>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(serviceArr[i]) + "'>" + "&#36;" + price.toFixed(2) + "</td>"
                + "<td id='" + serviceArr[i].serviceId + "service-check'>" + "<input type='hidden' name='service-check' id='" + serviceArr[i].serviceId + "'>" + "<input type='checkbox' value='" + JSON.stringify(serviceArr[i]) + "' class='c-select'>" + "</td>"
                + "</tr>");
    }
}; // end renderServiceList

var servicesClass = {
    renderSvcInfo: function (svcObj) {
        var svcInfoTable;
        var price = parseFloat(svcObj.price);
        var serviceTime = minutesFormat(svcObj.serviceTime);

        svcInfoTable = "<tr>" + "<td>" + "<input type='hidden' name='serviceObj' id='serviceObj' value='" + JSON.stringify(svcObj) + "'>" + "service id&#58;" + "</td>" + "<td>" + svcObj.serviceId + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "service name&#58;" + "</td>" + "<td>" + svcObj.name + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "description&#58;" + "</td>" + "<td>" + svcObj.description + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "category&#58;" + "</td>" + "<td>" + svcObj.category + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "service time&#58;" + "</td>" + "<td>" + serviceTime + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "service price&#58;" + "</td>" + "<td>" + "&#36;" + price.toFixed(2) + "</td>" + "</tr>"
                + "</tr>";

        return svcInfoTable;
    },
    renderEditSvc: function (svcObj) {
        var catOpts = serviceCategoryOpts(svcObj.category);
        var price = parseFloat(svcObj.price);
        var t = svcObj.serviceTime;
        var minuteOpts = timeConverter.minute(t);
        var hourOpts = timeConverter.hour(t);

        var editSvcInfo = "<tr>" + "<td>" + "<label for='service-id' title='service id'>service id&#58;</label></td>"
                + "<td>" + "<input type='text' disabled name='service-id' id='service-id' value='" + svcObj.serviceId + "'</td>" + "</tr>"
                + "<tr>" + "<td>" + "<label for='service-name'title='service name'>service name&#58;</label></td>"
                + "<td>" + "<input type='text' name='service-name' id='service-name' placeholder='required' title='please enter service name' required='please enter service name' value='" + svcObj.name + "' maxlength='45'>"
                + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "<label for='description' title='service description'>description&#58;</label></td>"
                + "<td>" + "<input type='text' name='description' id='description' title='please enter service description' required='please enter service description' value='" + svcObj.description + "' maxlength='45'>"
                + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "<label for='category' title='service category'>category&#58;</label></td>"
                + "<td>" + "<select name='category' id='category' required='please enter service category'>" + catOpts + "</select>"
                + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "<label for='time' title='service time'>time&#58;</label></td>"
                + "<td>" + "hour&#58;" + "<select name='hour' id='hour'  maxlength='10'>" + hourOpts + "</select>"
                + "&nbsp;minutes&#58;" + "<select name='minutes' id='minutes'  maxlength='10'>" + minuteOpts + "</select>"
                + "</td>" + "</tr>"
                + "<tr>" + "<td>" + "<label for='price' title='service price'>price&#58;</label></td>"
                + "<td>" + "&#36;&nbsp;<input type='number' name='price' id='price' max='9999' step='0.01'' required='please enter service price' value='"
                + price.toFixed(2) + "' size='4'>"
                + "</td>" + "</tr>";

        return editSvcInfo;
    }
}; // endservicesClass functions

var serviceCategoryOpts = function (cat) {
    var s = serviceList.getServiceCategories();
    var options = [];
    for (var i in s)
    {
        if (cat === s[i].name)
        {
            options[i] = "<option value='" + s[i].category_id + "' selected>" + s[i].name + "</option>";
        }
        else {
            options[i] = "<option value='" + s[i].category_id + "'>" + s[i].name + "</option>";
        }
    }
    return options;
};

function openMessage(svcObj) {
    $("#service-list-table").unbind("click").click();
    var editService = $("#editService");

    $("#services-tabs #services #service-info").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            OK: function () {
                $(this).dialog("close");
            },
            Edit: function () {
                var serviceObjElem = $("#service-info #serviceObj");
                var s = convert.parseJsonObj(serviceObjElem);
                editServiceInfo(s);

                editService.dialog("option", "title", "Update Service Information");
                editService.dialog("open");
                editService.dialog("option", "minHeight", 350);
                editService.dialog("option", "minWidth", 370);
                editService.dialog("option", "width", 450);
                editService.dialog("option", "maxWidth", 580);
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
} // end openMessage function

var editServiceInfo = function (svcObj) {

    var editServiceElem = $("#editService tbody");
    var serviceEditTable = servicesClass.renderEditSvc(svcObj);
    var tips = $("#editService .validateTips");
    tips.html("");

    svcObj1 = {
        serviceId: svcObj.serviceId,
        name: svcObj.name,
        description: svcObj.description,
        category_id: svcObj.category_id,
        price: svcObj.price,
        serviceTime: svcObj.serviceTime
    };

    editServiceElem.html(serviceEditTable);
    var serviceIdElem = $("#editService #service-id"),
            nameElem = $("#editServiceTable #service-name"),
            descriptionElem = $("#editService #description"),
            categoryElem = $("#editService #category"),
            hourElem = $("#editServiceTable select#hour"),
            minutesElem = $("#editServiceTable select#minutes"),
            priceElem = $("#editService #price");

//    var allFields = $([]).add(nameElem).add(descriptionElem).add(categoryElem).add(hourElem).add(minutesElem).add(priceElem);

    $("#services-tabs #services #editService").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            Save: function () {
                tips.html("");

                var serviceId = parseInt($("#editService #service-id").val()),
                        name = $("#editServiceTable #service-name").val(),
                        description = $("#editService #description").val(),
                        category_id = parseInt($("#editService #category").val()),
                        hours = parseInt($("#editServiceTable select#hour").val()),
                        min = parseInt($("#editServiceTable select#minutes").val()),
                        price = parseFloat($("#editService #price").val());

                var updateMsg = $("#services #postResults");
                updateMsg.html("");

                var bValid = true;
                bValid = bValid && validateForm.checkLength($("#editServiceTable #service-name"), "Service Name", 3, 45, tips);
                bValid = bValid && validateForm.checkLength($("#editService #description"), "Service Description", 3, 45, tips);
                bValid = bValid && validateForm.checkLength($("#editService #category"), "Service Category", 1, 11, tips);
                bValid = bValid && validateForm.checkRegexp($("#editService #price"), currencyRegEx, currencyErrorMsg, tips);

                if (hours === 0 && min === 0)
                {
                    validateForm.updateTips("Set the service time in minutes and hours", tips);
                    bValid = false;
                }
                var totalMins = moment.duration({minutes: min, hours: hours}).asMinutes();
                var svcObj2 = {
                    serviceId: serviceId,
                    name: name,
                    description: description,
                    category_id: category_id,
                    price: price,
                    serviceTime: totalMins
                };

                var noChanges = validateForm.noChanges(svcObj1, svcObj2); // any changes made
                if (noChanges === true) {
                    bValid = false;
                    validateForm.updateTips("No changes made to Service item.", tips);
                }

                if (bValid) {

                    var s = convert.stringifyObj(svcObj2);
                    var updateSuccess = doAjaxPost(s, '../FullCalPost', "updateService", false);

                    if (updateSuccess === true) {
                        serviceListing = serviceList.getServiceList();
                        renderServiceList(serviceListing);
//                        if (svcObj2.name !== svcObj1.name)
//                        {
                        // table row information
//                            $("td#" + svcObj2.serviceId + "service-name").html("<input type='hidden' id='serviceObj' name='serviceObj' value='" + JSON.stringify(svcObj2) + "'>" + svcObj2.name);
//                            fadeInMessage("td#" + svcObj2.serviceId + "service-name");  // flash in element that was changed

//                            $("td#" + svcObj2.serviceId + "service-name").addClass("ui-state-highlight");
//                            setTimeout(function () {
//                                tips.removeClass("ui-state-highlight", 1500);
//                            }, 500);
//                        }
                        $("tr#" + svcObj2.serviceId).addClass("successBkgColor"); // highlight table row that was changed
                    }

                    $(this).dialog("close");
                }
            },
            Cancel: function () {
                $(this).dialog("close");
            },
            Delete: function () {
            }
        },
        close: function () {
            tips.html("");
        }
    });
}; // end editServiceInfo function

var openConfirmation = function (svcObj) {
    var editService = $("#editService");

    $("#services-tabs #services #service-info").dialog({
        autoOpen: false,
        dialogClass: "no-close",
        modal: true,
        show: {
        },
        buttons: {
            OK: function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });
};

// sort by column functions
var sortServices = {
    byID: function (svcArray, isAscending) {
        svcArray["sortArrayN"] = "";
        svcArray["sortArrayD"] = "";
        svcArray["sortArrayC"] = "";
        svcArray["sortArrayT"] = "";
        svcArray["sortArrayP"] = "";
        var currentSort = svcArray["sortArrayID"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.serviceId > b.serviceId) {
                    return 1;
                }
                if (a.serviceId < b.serviceId) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayID"] = isAscending ? true : false;
        return svcArray;
    },
    byName: function (svcArray, isAscending) {
        svcArray["sortArrayID"] = "";
        svcArray["sortArrayD"] = "";
        svcArray["sortArrayC"] = "";
        svcArray["sortArrayT"] = "";
        svcArray["sortArrayP"] = "";
        var currentSort = svcArray["sortArrayN"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.name.toLowerCase() > b.name.toLowerCase()) {
                    return 1;
                }
                if (a.name.toLowerCase() < b.name.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayN"] = isAscending ? true : false;
        return svcArray;
    },
    byDescription: function (svcArray, isAscending) {
        svcArray["sortArrayID"] = "";
        svcArray["sortArrayN"] = "";
        svcArray["sortArrayC"] = "";
        svcArray["sortArrayT"] = "";
        svcArray["sortArrayP"] = "";
        var currentSort = svcArray["sortArrayD"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.description.toLowerCase() > b.description.toLowerCase()) {
                    return 1;
                }
                if (a.description.toLowerCase() < b.description.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayD"] = isAscending ? true : false;
        return svcArray;
    },
    byCategory: function (svcArray, isAscending) {
        svcArray["sortArrayID"] = "";
        svcArray["sortArrayN"] = "";
        svcArray["sortArrayD"] = "";
        svcArray["sortArrayT"] = "";
        svcArray["sortArrayP"] = "";
        var currentSort = svcArray["sortArrayC"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.category.toLowerCase() > b.category.toLowerCase()) {
                    return 1;
                }
                if (a.category.toLowerCase() < b.category.toLowerCase()) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayC"] = isAscending ? true : false;
        return svcArray;
    },
    byServiceTime: function (svcArray, isAscending) {
        svcArray["sortArrayID"] = "";
        svcArray["sortArrayN"] = "";
        svcArray["sortArrayD"] = "";
        svcArray["sortArrayC"] = "";
        svcArray["sortArrayP"] = "";
        var currentSort = svcArray["sortArrayT"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.serviceTime > b.serviceTime) {
                    return 1;
                }
                if (a.serviceTime < b.serviceTime) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayT"] = isAscending ? true : false;
        return svcArray;
    },
    byPrice: function (svcArray, isAscending) {
        svcArray["sortArrayID"] = "";
        svcArray["sortArrayN"] = "";
        svcArray["sortArrayD"] = "";
        svcArray["sortArrayC"] = "";
        svcArray["sortArrayT"] = "";
        var currentSort = svcArray["sortArrayP"];
        if (typeof currentSort !== 'boolean') {
            svcArray.sort(function (a, b) {
                if (a.price > b.price) {
                    return 1;
                }
                if (a.price < b.price) {
                    return -1;
                }
                // a must be equal to b
                return 0;
            });
        }
        else if (currentSort !== isAscending)
        {
            svcArray.reverse();
        }
        // set the sort order
        svcArray["sortArrayP"] = isAscending ? true : false;
        return svcArray;
    }
}; // end sortServices functions

// sort when column name is clicked
$(document).on("click", "#services-list-table  th:first-child", function (evt) {
    renderServiceList(serviceListing, "id");
});
$(document).on("click", "#services-list-table  th:nth-child(2)", function (evt) {
    renderServiceList(serviceListing, "name");
});
$(document).on("click", "#services-list-table  th:nth-child(3)", function (evt) {
    renderServiceList(serviceListing, "description");
});
$(document).on("click", "#services-list-table  th:nth-child(4)", function (evt) {
    renderServiceList(serviceListing, "category");
});
$(document).on("click", "#services-list-table  th:nth-child(5)", function (evt) {
    renderServiceList(serviceListing, "serviceTime");
});
$(document).on("click", "#services-list-table  th:nth-child(6)", function (evt) {
    renderServiceList(serviceListing, "price");
});

// toggle select all checboxes
$(document).on("click", "#select-header", function () {

    if (!isAllSelected)
    {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', true);
        });
        isAllSelected = true;
    }
    else if (isAllSelected)
    {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', false);
        });
        isAllSelected = false;
    }
    else {
        $(".c-select").each(function () {
            $(".c-select").prop('checked', true);
        });
        isAllSelected = true;
    }
}); // end select header on click function