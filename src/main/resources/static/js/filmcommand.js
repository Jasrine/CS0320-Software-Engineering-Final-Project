/* 
 * class that describes a Film node and methods for its display
 */
class Film {
	/*
	 * contains relevant information for the class 
	 */
	constructor(title, director, genres, year, runtime, crew) {
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.year = year;
		this.runtime = runtime;
		this.crew = crew;
	}

	renderFilmNode() {
		// create node and add relevant information to it.
	}
}

/*
 * function that gets the selected option when only one selection is allowed
 */
function getSelectedOption(sel) {
    let opt = 0;
    for (let i = 0; i < sel.options.length; i++) {
        opt = sel.options[i];
        if (opt.selected === true) {
        	break;
        }
    }

    return opt;
}

/*
 * helper for adding options with a given counter.
 */
function addOptions(res, sel, counter) {
	res.forEach(opt => {
		if (opt != null && opt != undefined) {
			const option = document.createElement("option");
			option.text = opt;
			option.setAttribute('value', counter);
			sel.add(option);
			counter = counter + 1;
		}
	});
}

// Global references
const $searchQuery = $("#search");
const $suggestions = $("#suggestions");
const regions = document.getElementById("regions");
const decades = document.getElementById("decades");
const genres = document.getElementById("genres");

$suggestions.children.onclick = ({

});

$(document).ready(() => {
	let region_i = 1;
	let genre_i = 1;
	let decade_i = 1;
	
	// initialization
	$.post("/init", {}, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		addOptions(responseObject.regions, regions, region_i);
		addOptions(responseObject.genres, genres, genre_i);
		addOptions(responseObject.decades, decades, decade_i);
	});

	($searchQuery).keyup(event => {
		if (event.which != 32) {	
			// console.log(getSelectedOption(regions));
			// console.log(getSelectedOption(decades));
			// console.log(getSelectedOption(genres));
			// console.log(getSelectedOption(regions).value);

			if (event.which == 13) {
				const region = (getSelectedOption(regions).value == 0) ? null : getSelectedOption(regions).text;
				const genre = (getSelectedOption(genres).value == 0) ? null : getSelectedOption(genres).text;
				const decade = (getSelectedOption(regions).value == 0) ? null : getSelectedOption(decades).text;

				const params = {
					"search": $searchQuery.val(),
					"genres": genre,
					"regions": region,
					"decades": decade
				};
				
				// if a user presses enter, search through database with the options the user has selected.
				// $.post("/search", params2, responseJSON => {
				// 	const responseObject = JSON.parse(responseJSON);

				// 	$suggestions.text(responseObject.suggestions);
				// 	$suggestions.html(responseObject.suggestions);
				// 	$suggestions.val(responseObject.suggestions);
	   //      		console.log(responseObject);
	   //      		console.log(responseObject.suggestions);
				// });
			} else {
				const params = {
					"search": $searchQuery.val()
				};
				$.post("/suggest", params, responseJSON => {
					const responseObject = JSON.parse(responseJSON);
					let i = 0;

					for (let j = $suggestions[0].children.length-1; j >= 0; j--) {
						$suggestions[0].removeChild($suggestions[0].children[j]);
					}
					responseObject.suggestions.split("\n").forEach(suggestion => {
						const $node = $("<li class=\"ui-widget-content\">").text(suggestion);
						$suggestions.append($node);
						i = i + 1;
					});
				});
			}
		}
	});
});