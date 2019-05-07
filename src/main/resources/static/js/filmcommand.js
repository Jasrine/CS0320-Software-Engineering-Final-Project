const $loadingDiv = document.getElementById("loading style-2");

/* 
 * class that describes a Film node and methods for its display
 */
class Film {
	/*
	 * contains relevant information for the class 
	 */
	constructor(id, title, director, genres, year, regions, img, cast, numVotes, rating) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.year = year;
		this.regions = regions;
		this.img = img;
		this.cast = cast;
		this.numVotes = numVotes;
		this.rating = rating;
	}

	toString() {
		let region = this.regions.join(", ");
		let line = region;
		let regionStr = "";
		while (line.length > 80) {
			let piece = line.substring(0, 80);
			let lastSpace = piece.lastIndexOf(", ");
			piece = piece.substring(0, lastSpace);
			regionStr = regionStr + piece.concat("\n");
			line = line.substring(lastSpace + 1, line.length - 1);
		}

		if (regionStr.length < 1) {
			regionStr = region;
		}

		//let textNode = document.createElement('b').appendChild(document.createTextNode("Film title: "));
		//textNode.appendChild(document.createTextNode(this.title));
		

		let resultStr = " Film title: " + this.title;
		if (this.director != undefined && this.director != null && this.director.trim().length > 0) {
			// textNode.appendChild(document.createElement('br'));
			// textNode.appendChild(document.createElement('b').appendChild(
			// 	document.createTextNode("Director: ")));
			// textNode.appendChild(document.createTextNode(this.director));
			resultStr += ("\n Director: " + this.director);
		}
		if (this.genres != undefined && this.genres != null && this.genres.length > 0 && this.genres[0].trim().length > 0) {
			// textNode.appendChild(document.createElement('br'));
			// textNode.appendChild(document.createElement('b').appendChild(
			// 	document.createTextNode("Genres: ")));
			// textNode.appendChild(document.createTextNode(this.genres.join(", ")));
			resultStr += ("\n Genres: " + this.genres.join(", "));
		}
		if (parseInt(this.year) > 0) {
			resultStr += ("\n Year in which this premiered: " + this.year);
		}
		if (regionStr != undefined && regionStr != null && regionStr.length > 0) {
			resultStr += ("\n Regions: " + regionStr.trim());
		}
		if (this.cast != undefined && this.cast != null && this.cast.length > 0 && this.cast[0].trim().length > 0) {
			let castLine = this.cast.join(", ").trim();
			let castStr = "";
			while (castLine.length > 80) {
				let piece = castLine.substring(0, 80);
				let lastSpace = piece.lastIndexOf(", ");
				piece = piece.substring(0, lastSpace);
				castStr = castStr + piece.concat("\n");
				castLine = castLine.substring(lastSpace + 1, line.length - 1);
			}
			if (castStr.length < 1) {
				castStr = castLine;
			}

			resultStr += ("\n Cast: " + castStr.trim());
		}

		return resultStr;
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
 * load an image using the url.
 */
function preloadImage(url) {
    let img = new Image();
	img.src = url;
    return img;
}

function switchLoading() {
  if ($loadingDiv.style.display === "none") {
    $loadingDiv.style.display = "block";
  } else {
    $loadingDiv.style.display = "none";
  }
}

/*
 * helper for adding options with a given counter.
 */
function addOptions(res, sel, counter) {
	let counter_2 = counter;
	res.forEach(opt => {
		if (opt != null && opt != undefined) {
			const option = document.createElement("option");
			option.text = opt;
			option.setAttribute('value', counter);
			sel.add(option);
			counter = counter + 1;
			counter_2 = counter;
		}
	});

	return counter_2;
}

function wait(ms){
   var start = new Date().getTime();
   var end = start;
   while(end < start + ms) {
     end = new Date().getTime();
  }
}

// Global references
const $searchQuery = $("#search");
const $suggestions = $("#suggestions");
const $searchResults = $("#searchResults");
const currFilm = document.getElementById("currFilm");
const regions = document.getElementById("regions");
const decades = document.getElementById("decades");
const genres = document.getElementById("genres");
const services = document.getElementById("services");
const index = document.getElementById("index2");

document.getElementById("searchResults").addEventListener("click", function(e) {
    if (e.target && e.target.matches("td")) {
	  	const f = e.target.potato;
	  	let castString = "";
	  	for (let i = 0; i < f.cast.length; i++) {
	  	  castString = castString + "," + f.cast[i];
        }
	  	const params = {
			id: f.id,
			filmName: f.title,
			director: f.director,
			img: f.img,
			genreA: f.genres[0],
            genreB: f.genres[1],
            genreC: f.genres[2],
			year: f.year,
			regions: f.regions,
			rating: f.rating,
			votes: f.numVotes,
			// cast: f.cast,
            cast: castString,
		};
		console.log(params);
   	$.post("/similarity", params, responseJSON => {
   		const responseObject = JSON.parse(responseJSON);
   		index.style = "display:none;";
   		for (let j = $searchResults[0].children.length-1; j >= 0; j--) {
			$searchResults[0].removeChild($searchResults[0].children[j]);
		}

		currFilm.innerHTML = f.toString();

   		const $node = document.createElement('table');
		$node.setAttribute('class', 'film-widget-table');
   		responseObject.results.forEach(suggestion => {
			let f = new Film(suggestion.id, suggestion.filmName, suggestion.director, suggestion.genres,
							suggestion.year, suggestion.regions, suggestion.img, suggestion.cast);
			var row = document.createElement("tr");
			row.setAttribute('class', 'nested-film-widget');
			row.setAttribute('innerHTML', f.toString());
			row.setAttribute('innerText', f.toString());
			let cell = document.createElement("td");
			let cellText = document.createTextNode(f.toString());
			cell.potato = f;
			cell.appendChild(cellText);
			row.appendChild(cell);
			if (suggestion.img != null && suggestion.img != undefined && suggestion.img.length > 0) {
				let img = preloadImage(suggestion.img);
			    if (img == null || img == undefined) {
					img = preloadImage('/css/images/Question-Mark.png');
				}
				let cell2 = document.createElement("td");
				img.setAttribute('class', 'film-widget-td-img');
				cell2.appendChild(img);
				row.appendChild(cell2);
			} else {
				let img = preloadImage('/css/images/Question-Mark.png');
				let cell2 = document.createElement("td");
				img.setAttribute('class', 'film-widget-td-img');
				cell2.appendChild(img);
				row.appendChild(cell2);
			}
			$node.appendChild(row);
		});
		$searchResults.append($node);
  	});
  	const val = e.target.node;
    $searchQuery.value = e.target.innerHTML;
    $searchQuery.val(e.target.innerHTML);
  }
});

document.getElementById("suggestions").addEventListener("click", function(e) {
  if (e.target && e.target.matches("td.suggestions-widget-td")) {
    $searchQuery.value = e.target.innerHTML;
    $searchQuery.val(e.target.innerHTML);
    for (let j = $suggestions[0].children.length-1; j >= 0; j--) {
		$suggestions[0].removeChild($suggestions[0].children[j]);
	}
  }
});

$(document).ready(() => {
	let region_i = 1;
	let genre_i = 1;
	let decade_i = 1;
	let service_i = 1;

	if (region_i < 2) {
		wait(8000);
	
		$.post("/init", {}, responseJSON => {
			const responseObject = JSON.parse(responseJSON);
			region_i = addOptions(responseObject.regions, regions, region_i);
			genre_i = addOptions(responseObject.genres, genres, genre_i);
			decade_i = addOptions(responseObject.decades, decades, decade_i);
			service_i = addOptions(responseObject.services, services, service_i);
			
		});
	}
	console.log("done with init");
	switchLoading();

	($searchQuery).keyup(event => {
		if (event.which != 32) {	
			const region = (getSelectedOption(regions).value == 0) ? "" : getSelectedOption(regions).text;
			const genre = (getSelectedOption(genres).value == 0) ? "" : getSelectedOption(genres).text;
			const decade = (getSelectedOption(decades).value == 0) ? "" : getSelectedOption(decades).text;
			const service = (getSelectedOption(services).value == 0) ? "" : getSelectedOption(services).text;
			const params = {
				"search": $searchQuery.val(),
				"genre": genre,
				"region": region,
				"decade": decade,
				"service": service
			};
			console.log(params);
			// when enter is pressed
			if (event.which == 13) {
				// if a user presses enter, search through database with the options the user has selected.
				for (let j = $suggestions[0].children.length-1; j >= 0; j--) {
					$suggestions[0].removeChild($suggestions[0].children[j]);
				}

				$.post("/search", params, responseJSON => {
					const responseObject = JSON.parse(responseJSON);
					console.log(responseObject);

					for (let j = $searchResults[0].children.length-1; j >= 0; j--) {
						$searchResults[0].removeChild($searchResults[0].children[j]);
					}
					const $node = document.createElement('table');
					$node.setAttribute('class', 'film-widget-table');
					responseObject.results.forEach(suggestion => {
						let f = new Film(suggestion.id, suggestion.filmName, suggestion.director, suggestion.genres,
							suggestion.year, suggestion.regions, suggestion.img, suggestion.cast);
						var row = document.createElement("tr");
						row.setAttribute('class', 'film-widget');
						row.setAttribute('innerHTML', f.toString());
						row.setAttribute('innerText', f.toString());
						var cell = document.createElement("td");
						var cellText = document.createTextNode(f.toString());
						cell.potato = f;
						cell.appendChild(cellText);
						row.appendChild(cell);
						if (suggestion.img != null && suggestion.img != undefined && suggestion.img.length > 0) {
							let img = preloadImage(suggestion.img);
							if (img == null || img == undefined) {
								img = preloadImage('/css/images/Question-Mark.png');
							}
							var cell2 = document.createElement("td");
							img.setAttribute('class', 'film-widget-td-img');
							cell2.appendChild(img);
							row.appendChild(cell2);
						} else {
							let img = preloadImage('/css/images/Question-Mark.png');
							var cell2 = document.createElement("td");
							img.setAttribute('class', 'film-widget-td-img');
							cell2.appendChild(img);
							row.appendChild(cell2);
						}
						$node.appendChild(row);
					});
					$searchResults.append($node);
				});
			} else {
				console.log(params);
				$.post("/suggest", params, responseJSON => {
					const responseObject = JSON.parse(responseJSON);
					let i = 0;
					for (let j = $suggestions[0].children.length-1; j >= 0; j--) {
						$suggestions[0].removeChild($suggestions[0].children[j]);
					}
					const $node = document.createElement('table');
					$node.setAttribute('class', 'suggestions-widget-table');
					responseObject.suggestions.split("\n").forEach(suggestion => {
						let row = document.createElement("tr");
						row.setAttribute('class', 'suggestions-widget');
						let cell = document.createElement("td");
						cell.setAttribute('class', 'suggestions-widget-td');
						let cellText = document.createTextNode(suggestion);
						cell.appendChild(cellText);
						row.appendChild(cell);
						$node.appendChild(row);
						i = i + 1;
					});
					$suggestions.append($node);
				});
			}
		}
	});
});