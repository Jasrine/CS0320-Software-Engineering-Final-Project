const $loadingDiv = document.getElementById("loading style-2");

/* 
 * class that describes a Film node and methods for its display
 */
class Film {
	/*
	 * contains relevant information for the class 
	 */
	constructor(id, title, director, genres, year, regions, img) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.year = year;
		this.regions = regions;
		this.img = img;
	}

	renderFilmNode() {
		// create node and add relevant information to it.
	}

	toString() {
		let region = this.regions.join(", ");
		//console.log(region);
		console.log(this.regions.join(", "));
		let line = region;
		let regionStr = "";
		while (line.length > 80) {
			let piece = line.substring(0, 80);
			let lastSpace = piece.lastIndexOf(", ");
			piece = piece.substring(0, lastSpace);
			console.log(piece);
			regionStr += (piece.concat("\n"));
			line = line.substring(lastSpace + 1, line.length - 1);
		}
		if (regionStr.length < 1) {
			regionStr = region;
		}

		let resultStr = " Film title: " + this.title;
		if (this.director.trim().length > 0) {
			resultStr += ("\n Director: " + this.director);
		}
		if (this.genres.length > 0) {
			resultStr += ("\n Genres: " + this.genres);
		}
		if (parseInt(this.year) > 0) {
			resultStr += ("\n Year in which this premiered: " + this.year);
		}
		if (this.regionStr != undefined && this.regionStr != null && this.regionStr.length > 0) {
			resultStr += ("\n Regions: " + regionStr);
		}

		return resultStr;//this.regions.join(", ");
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

    // let imgOriginal = new Image();
    // imgOriginal.src = url;
    // console.log(imgOriginal)
    // console.log(imgOriginal.height);
    // console.log(imgOriginal.width);

    // let max = Math.max(imgOriginal.width, imgOriginal.height);
    // let min = Math.min(imgOriginal.width, imgOriginal.height);

    // let newHeight = (imgOriginal.height/float(max)) * 100;
    // let newWidth = (imgOriginal.width/float(max)) * 100;

    // let widthBorderLeft = 50 - newWidth/2;
    // let widthBorderRight = 50 + newWidth/2;
    // let heightBorderTop = 50 - newHeight/2;
    // let heightBorderBottom = 50 + newHeight/2;

	img.src = url;
    if (img.width > img.height) {
    	img.style.width = '120px';
    	img.style.height = 'auto';
    } else {
    	img.style.height = '120px';
    	img.style.width = 'auto';
    }
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
const $searchResults = $("#searchResults");
const regions = document.getElementById("regions");
const decades = document.getElementById("decades");
const genres = document.getElementById("genres");
const services = document.getElementById("services");

document.getElementById("searchResults").addEventListener("click", function(e) {
	console.log(e);
  if (e.target && e.target.matches("p.film-widget")) {

  // 	const params = {

  // 	};
  // 	$.post("/similarity", params, responseJSON => {
  // 		const responseObject = JSON.parse(responseJSON);
  // 		responseObject.results.forEach(suggestion => {
		// 	let f = new Film(suggestion.id, suggestion.filmName, suggestion.director, suggestion.genres,
		// 		suggestion.year, suggestion.regions, suggestion.img);


		// 	console.log(suggestion);
		// 	const $node = document.createElement('p');//$("<li class=\"ui-widget-content\">").text(f.toString());
		// 	$node.setAttribute('class', 'nested-film-widget');
		// 	$node.setAttribute('innerHTML', f.toString());
		// 	$node.setAttribute('innerText', f.toString());
		// 	$node.textContent = f.toString();
		// 				//$node.style = "opacity: 0.5; padding-left: 5px; text-align: left;";
		// 	$nestedResults.append($node);
		// 	console.log($node);
		// 	if (suggestion.img != null && suggestion.img != undefined && suggestion.img.length > 0) {
		// 		let img = preloadImage(suggestion.img);
		// 					// img.width = 300;
		// 					// img.height = 300;
		// 		if (img == null || img == undefined) {
		// 			img = preloadImage('/css/images/Question-Mark.png');
		// 		}
		// 		$nestedResults.append(img);
		// 	} else {
		// 		let img = preloadImage('/css/images/Question-Mark.png');
		// 		$searchResults.append(img);
		// 	}
		// });
  // 	}
  	const val = e.target.node;

  	console.log("foo");
    //e.target.style.color = "#9932CC";
    $searchQuery.value = e.target.innerHTML;
    $searchQuery.val(e.target.innerHTML);
  }
});

document.getElementById("suggestions").addEventListener("click", function(e) {
	console.log(e);
  if (e.target && e.target.matches("p.suggestions-widget")) {
  	console.log("foo");
    //e.target.style.color = "#9932CC";
    $searchQuery.value = e.target.innerHTML;
    $searchQuery.val(e.target.innerHTML);
  }
});

$(document).ready(() => {
	let region_i = 1;
	let genre_i = 1;
	let decade_i = 1;
	let service_i = 1;

	// initialization
	$.post("/init", {}, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		addOptions(responseObject.regions, regions, region_i);
		addOptions(responseObject.genres, genres, genre_i);
		addOptions(responseObject.decades, decades, decade_i);
		addOptions(responseObject.services, services, service_i);
		
	});
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
				$.post("/search", params, responseJSON => {
					const responseObject = JSON.parse(responseJSON);
					console.log("TEST!!!\n");
					console.log(responseObject);

					for (let j = $searchResults[0].children.length-1; j >= 0; j--) {
						$searchResults[0].removeChild($searchResults[0].children[j]);
					}
					responseObject.results.forEach(suggestion => {
						let f = new Film(suggestion.id, suggestion.filmName, suggestion.director, suggestion.genres,
							suggestion.year, suggestion.regions, suggestion.img);


						console.log(suggestion);
						const $node = document.createElement('p');//$("<li class=\"ui-widget-content\">").text(f.toString());
						$node.setAttribute('class', 'film-widget');
						$node.setAttribute('innerHTML', f.toString());
						$node.setAttribute('innerText', f.toString());
						$node.textContent = f.toString();
						//$node.style = "opacity: 0.5; padding-left: 5px; text-align: left;";
						$searchResults.append($node);
						console.log($node);
						if (suggestion.img != null && suggestion.img != undefined && suggestion.img.length > 0) {
							let img = preloadImage(suggestion.img);
							// img.width = 300;
							// img.height = 300;
							if (img == null || img == undefined) {
								img = preloadImage('/css/images/Question-Mark.png');
							}
							$searchResults.append(img);
						} else {
							let img = preloadImage('/css/images/Question-Mark.png');
							$searchResults.append(img);
						}
					});
				});
			} else {
				console.log("SUGGEST");
				console.log(params);
				$.post("/suggest", params, responseJSON => {
					const responseObject = JSON.parse(responseJSON);
					console.log(responseObject);
					let i = 0;
					for (let j = $suggestions[0].children.length-1; j >= 0; j--) {
						$suggestions[0].removeChild($suggestions[0].children[j]);
					}
					responseObject.suggestions.split("\n").forEach(suggestion => {
						const $node = document.createElement('p');
						$node.setAttribute('class', 'suggestions-widget'); //$("<li class=\"suggestions-widget\">").text(suggestion);
						$node.textContent = suggestion;
						$suggestions.append($node);
						i = i + 1;
					});
				});
			}
		}
	});
});