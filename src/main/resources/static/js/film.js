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

		return "Film title: " + this.title + "\n Director: " + this.director + "\n Genres: " + this.genres 
		+ "\n Year in which this premiered: " + this.year + "\n Regions: " + regionStr;//this.regions.join(", ");
	}
}