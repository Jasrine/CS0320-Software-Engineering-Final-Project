

$(document).ready(() => {
	const $searchQuery = $("#search");
	const $suggestions = $("#suggestions");
	const regions = document.getElementById("regions");
	let region_i = 0;
	const params = {};
	console.log(regions);

	$.post("/init", params, responseJSON => {
		const responseObject = JSON.parse(responseJSON);
		console.log(responseObject);
		responseObject.regions.forEach(region => {
			if (region != null && region != undefined) {
				const option = document.createElement("option");
				option.text = region;
				console.log(region);
				console.log(option);
				regions.add(option);

				region_i = region_i + 1;
			}
		});
	});

	($searchQuery).keyup(event => {
		if (event.which != 32) {
			params = {
				"search": $searchQuery.val()
			};
			console.log(params);
			$.post("/suggest", params, responseJSON => {
				const responseObject = JSON.parse(responseJSON);

				$suggestions.text(responseObject.suggestions);
				$suggestions.html(responseObject.suggestions);
				$suggestions.val(responseObject.suggestions);
        		console.log(responseObject);
        		console.log(responseObject.suggestions);
			});
		}
	});
});