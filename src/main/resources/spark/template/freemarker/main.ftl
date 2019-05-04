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
            cursor: pointer;
            white-space: pre;
            text-align: center;
            vertical-align: center;
            background: #DA70D6;
            border: 1px solid #9370DB;
            color: #333333;
        }
        .ui-widget-content .selected {
            color: #9932CC
        }
        .ui-widget-content:active {
            color: #9932CC
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

            <div class="loading style-2" id="loading style-2"><div class="loading-wheel"></div></div>
            Enter a possible film title:<br>
            <input type="text" id="search" name="search"><br>
            <ol name="suggestions" id="suggestions"></ol>

            From a year: <select name="decades" id="decades">
                <option value="0">Select a decade.</option>
            </select><br>

    <!--         <div class="column1"> -->
                Regions:
<!--             </div>
            <div class="column2"> -->
            <select name="regions" id="regions">
                <option value="0">Select a region.</option>
            </select><br>
        <!-- </div><br>
 -->
            &/or belonging to a genre:
            <select name="genres" id="genres">
                <option value="0">Select a genre.</option>
            </select><br>

            &/or from a streaming service:
            <select name="services" id="services">
                <option value="0">Select a streaming service.</option>
            </select><br>
            Here are suggestions:<br>

            <ol name="searchResults" id="searchResults">
            </ol>
            <!-- <select name="suggestions" id="suggestions" size="5">
            </select>
     --><!-- 
            <textarea name="suggestions" id="suggestions"></textarea> -->
            <p style="white-space: nowrap;"> <a href="/contact">Contact us</a> <a href="/about">About us</a> <a href="/faq">Anticipated Questions</a></p>
        </div>
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/filmcommand.js"></script>  

</body>
</html>