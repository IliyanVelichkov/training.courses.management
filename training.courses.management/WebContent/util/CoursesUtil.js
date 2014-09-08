jQuery.sap.declare("training.courses.management.util.CoursesUtil");

jQuery.sap.require("sap.ui.core.format.DateFormat");

training.courses.management.util.CoursesUtil = {

	filterCourses : function(courses) {
		var aTitles = [];
		for (var idx = 0; idx < courses.length; idx++) {
			var courseTitle = courses[idx].title;
			if (aTitles.indexOf(courseTitle) != -1 || training.courses.management.util.Helper.isInvalidString(courseTitle)) {
				courses.splice(idx, 1);
				idx--;
				continue;
			}
			aTitles.push(courseTitle);
		}
	},
};
