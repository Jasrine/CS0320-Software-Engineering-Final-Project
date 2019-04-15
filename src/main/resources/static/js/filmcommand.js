

$(document).ready(() => {
	const $searchQuery = $("#search");
	($searchQuery).keyup(event => {
		if (event.which != 32) {
			params = {
				"search": $searchQuery.val()
			};
			console.log(params);
			$.post("/suggest", params, responseJSON => {
				const responseObject = JSON.parse(responseJSON);
        		console.log(responseObject);
			});
		}
	});
});