<#assign content>
    <form method="GET" action="/results">
        <fieldset>
            <legend>Enter a search query:</legend>
            <p>
                Enter a search query : <input type="text" id="from_by_coordinates" name="from_by_coordinates">
            </p>
        </fieldset>
        <fieldset>
            <legend>Advanced search:</legend>
            <p>
                By (director) : <input type="text" id="from_by_name" name="from_by_name">
            </p>
            <p>
                Involving (names of actors and/or writers) :  <input type="text" id="to_by_name" name="to_by_name">
            </p>
        </fieldset>
    <input type="submit">
    </form>

</#assign>
<#include "main.ftl">