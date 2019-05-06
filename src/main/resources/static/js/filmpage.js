	$.post("/similarity", params, responseJSON => {
   		const responseObject = JSON.parse(responseJSON);
   		responseObject.results.forEach(suggestion => {
			let f = new Film(suggestion.id, suggestion.filmName, suggestion.director, suggestion.genres,
		 		suggestion.year, suggestion.regions, suggestion.img, suggestion.cast, suggestion.numVotes,
		 		suggestion.rating);
			console.log(suggestion);
			const $node = document.createElement('p');
			$node.setAttribute('class', 'nested-film-widget');
			$node.setAttribute('innerHTML', f.toString());
			$node.setAttribute('innerText', f.toString());
			$node.textContent = f.toString();
			$searchResults.append($node);
			console.log($node);
			if (suggestion.img != null && suggestion.img != undefined && suggestion.img.length > 0) {
				let img = preloadImage(suggestion.img);
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

  	$(document).ready(() => {});