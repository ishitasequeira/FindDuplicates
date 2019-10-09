<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Potential Duplicates from file</title>
    <script type="text/javascript" src="/resources/js/app.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <style>
        #nonDuplicatesDiv {
            display: none;
        }
    </style>
</head>
<body>
    <br/><br/>
    <div class="container">
        <div class="row">
            <a href="/" class="btn btn-primary btn-block">Back</a>
        </div>
        <br/>
        <hr>
        <h3>Select your option:</h3>
        <select name='optionSelect' id="optionSelect" onchange="toggleDisplay()">
            <option value="Duplicates">Duplicates</option>
            <option value="Non-Duplicates">Non-Duplicates</option>
        </select>
        <br/><hr><br/>
        <div class="row" id="duplicatesDiv">
            <h3>List of Potential Duplicate Entries</h3>
            <table id="duplicateTable" class="table table-sm">
                <thead>
                    <tr>
                        <c:forEach items="${headers}" var="columnName">
                            <th class="col">${columnName}</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${duplicates}" var="setOfMaps">
                        <c:forEach items="${setOfMaps}" var="mapOfAttributes">
                            <tr>
                                <c:forEach items="${mapOfAttributes}" var="attr">
                                    <td><c:out value="${attr.value}"></c:out></td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row" id="nonDuplicatesDiv">
            <h3>List of Non-Duplicate Entries</h3>
            <table id="nonDuplicates" class="table table-sm">
                <thead>
                <tr>
                    <c:forEach items="${headers}" var="colname">
                        <th class="col">${colname}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${nonDuplicates}" var="mapOfAttributes">
                    <tr>
                        <c:forEach items="${mapOfAttributes}" var="attr">
                            <td><c:out value="${attr.value}"></c:out></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
<script>
    function toggleDisplay() {
        var dropdown = document.getElementById("optionSelect").value;
        if(dropdown === "Duplicates") {
            document.getElementById("duplicatesDiv").style.display = "block";
            document.getElementById("nonDuplicatesDiv").style.display = "none";
        } else {
            document.getElementById("duplicatesDiv").style.display = "none";
            document.getElementById("nonDuplicatesDiv").style.display = "block";
        }
    }
</script>
</html>