<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/mainstyle.css">
    <link rel="stylesheet" href="css/jquery-ui.css">
    <script src="js/jquery-1.10.2.js"></script>
    <script src = "js/jquery-ui.js"></script>
    
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
            list-style-type: none;
        }
        .errorData {
            text-emphasis-color: red;
        }
        #messages {
            text-emphasis-color: red;
        }

        #selectable .ui-selecting { 
            background: #707070; 
        }
        #selectable .ui-selected { 
            background: #EEEEEE; 
            color: #000000; }
        #selectable { 
            width: 20%; 
        }
        #selectable li { 
            margin: 3px; 
            padding: 1em; 
            font-size: 16px; 
            height: 25px; 
            width: 500px;
        }
        .ui-widget-content {
            text-align: center;
            vertical-align: center;
            background: #DA70D6;
            border: 1px solid #9370DB;
            color: #333333;
        }
        #selectable li .hover {
            color: #BA55D3
        }
        #selectable li .active {
            color: #BA55D3
        }
    </style>
</head>
<body>
    <div class="outer" id="outer">
        <div class="index" id="index">
            Enter a possible film title:<br>
            <input type="text" id="search" name="search"><br>
            From a year: <select name="decades" id="decades">
                <option value="0">Select a decade.</option>
            </select><br>

            &/or from a place:
            <select name="regions" id="regions">
                <option value="0">Select a region.</option>
            </select><br>

            &/or belonging to a genre:
            <select name="genres" id="genres">
                <option value="0">Select a genre.</option>
            </select><br>
            Here are suggestions:<br>

            <ol name="suggestions" id="suggestions">
                <li class="ui-widget-content">Item 1</li>
                <li class="ui-widget-content">Item 2</li>
                <li class="ui-widget-content">Item 3</li>
                <li class="ui-widget-content">Item 4</li>
                <li class="ui-widget-content">Item 5</li>
            </ol>
            <!-- <select name="suggestions" id="suggestions" size="5">
            </select>
     --><!-- 
            <textarea name="suggestions" id="suggestions"></textarea> -->

        </div>
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/filmcommand.js"></script>  

</body>
</html>