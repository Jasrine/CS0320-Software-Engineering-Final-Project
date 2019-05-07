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
            <a class = 'filmdb' href="http://localhost:4567/filmdb"><img src="https://fontmeme.com/permalink/190506/675802bc1aed73ae426e940588e6d752.png" alt="glacial-indifference-font" border="0"></a>
            <BR><BR>

            <div class="index" id="index2">
                Enter a possible film title:<br>
                <input type="text" id="search" name="search">
                <label name="suggestions" id="suggestions"></label><BR>

                From a year: <select name="decades" id="decades">
                    <option value="0">Select a decade.</option>
                </select><br>

                Regions:
                <select name="regions" id="regions">
                    <option value="0">Select a region.</option>
                </select><br>
                &/or belonging to a genre:
                <select name="genres" id="genres">
                    <option value="0">Select a genre.</option>
                </select><br>

                &/or from a streaming service:
                <select name="services" id="services">
                    <option value="0">Select a streaming service.</option>
                </select><br>
                Here are suggestions:<br>
                <div class="search" id="result" name="result"><search><img src="css/images/play4.png" height="70" width="70"></search></div>
            </div>

            <p name="currFilm" id="currFilm" class="currFilm"></p>
            <p name="searchResults" id="searchResults"></p>
        </div>
    </div>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/filmcommand.js"></script>  

</body>
</html>