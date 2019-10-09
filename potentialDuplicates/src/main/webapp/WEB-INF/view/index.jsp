<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
    <div>
        <form method="POST" action="/potentialDuplicates" enctype="multipart/form-data">
            <div class="px-4 py-3">
            <div class="form-group" style="padding: 2% 5%">
                <h3>Find All Potential Duplicates from a CSV</h3><br/><br/>
                <label> Upload file to Find Duplicates</label><br/>
                <input type="file" name="file" required accept="text/csv"/><br/><br/><br/>
                <input style="width: 18%" type="submit" value="Submit"/>
            </div>
            </div>
        </form>
    </div>
</body>
</html>