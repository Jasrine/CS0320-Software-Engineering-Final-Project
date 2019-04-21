<!DOCTYPE html>

<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/baconstyle.css">
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
        #tableForInput {
            border-spacing: 10px;
            border-collapse: separate;
            text-align: center;
            align-content: center;
        }
    </style>
</head>
<body>
<div class="bg-image"> </div>
    <div class="bg-text">
        <h1 style="font-size:50px">Maps!</h1>
        <h3>Curious about how to go from one place to another? Check this out!</h3>
        <fieldset>
          <legend>Which database do you want to work on? (Press Enter Key after filling up Data Path)</legend>
         <p>
             <label for="databaseInput"></label><input type="text" name="databaseIn" placeholder="Example: data/maps/maps.sqlite3" id="database"> <br>
         </p>
         <p id="outputDatabase">
         </p>
        </fieldset>
        <form method="GET" action="/baconResult">
        <table id="tableForInput" align="center">
            <tr>
                <th>
                    <fieldset>
                        <legend>From</legend>
                        <p>
                            <input id="from_source" type="text" placeholder="Example: Thayer Street" name="from_source"> <br>
                        </p>
                    </fieldset>
                </th>

                <th>
                    <fieldset>
                        <legend>To</legend>
                        <p>
                            <input id="to_destination" type="text" placeholder="Example: Angell Street" name="to_destination"> <br>
                        </p>
                    </fieldset>
                </th>
            </tr>
            <tr>
                <td>
                    <div id="suggOfFrom">
                        <ul id="suggestionsOfFrom"></ul>
                    </div>
                </td>
                <td>
                    <div id="suggOfTo">
                        <ul id="suggestionsOfTo"></ul>
                    </div>
                </td>
            </tr>
        </table>
        <input type="submit" id="submitToCanvas">
        </form>
    </div>

<canvas id="board"></canvas>
<!-- Again, we're serving up the unminified source for clarity. -->
<script src="js/jquery-2.1.1.js"></script>

<script type="text/javascript" src="js/baconfirst.js"></script>
</body>
<!-- See http://html5boilerplate.com/ for a good place to start
     dealing with real world issues like old browsers.  -->
</html>