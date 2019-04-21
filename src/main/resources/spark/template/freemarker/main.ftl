<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/mainstyle.css">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }
        * {
            box-sizing: border-box;
        }
        ul {
            padding: 5px;
            list-style-type: none;
        }
        .errorData {
            text-emphasis-color: red;
        }
        #messages {
            text-emphasis-color: red;
        }
    </style>
</head>
<body>
    <div class="index" id="index">
        Enter a possible film title:<br>
        <input type="text" id="search" name="search"><br>
        From a year:<br>
        <select name="decades" id="decades"></select><br>
        &/or from a place:<br>
        <select name="regions" id="regions"></select><br>
        Here are suggestions:<br>
        <!-- <select name="suggestions" id="suggestions" size="5">
        </select>
 -->
        <textarea name="suggestions" id="suggestions"></textarea>

    </div>
<script src="js/jquery-2.1.1.js"></script>
<script src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/filmcommand.js"></script>  

</body>
</html>