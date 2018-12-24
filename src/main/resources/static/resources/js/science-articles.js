$(document).ready(function () {

    var scienceController = { // Its an example of javascript module
        rootUrl: "/science-articles",
        init: function () { // This init function is calling at the end of this script
            this.bindLoadArticlesButton();
            this.bindAddUpdateArticleButtons();
            this.bindDetailsEditAndDeleteButtons();
        },
        bindLoadArticlesButton: function () {
            $(document).on('click', '#btn-load-science-articles', this.loadScienceArticles.bind(this));
        },
        loadScienceArticles: function () {
            this.clearTable();

            var self = this;
            $.get(this.rootUrl).done(function (scienceArticles) {
                console.log(scienceArticles);
                if (!scienceArticles) alert("Sorry! We are facing an issue.");

                scienceArticles.forEach(function (scienceArticle) {
                    self.addScienceArticleToTable(scienceArticle);
                })
            }).fail(function (error) {
                console.log("Error on articles fetch :", error);
                alert("Sorry! We are facing an issue.");
            })
        },
        clearTable: function () {
            $("#science-articles-tbody").html("");
        },
        bindAddUpdateArticleButtons: function () {
            $(document).on('click', '#btn-add-science-articles', this.saveScienceArticle.bind(this));
            $(document).on('click', '#btn-update-science-articles', this.updateScienceArticle.bind(this));
        },
        saveScienceArticle: function () {
            var addContainer = $('#add-update-science-article'),
                titleContainer = addContainer.find('.sa-title'),
                descriptionContainer = addContainer.find('.sa-description'),
                authorContainer = addContainer.find('.sa-author');

            var newArticle = {title: titleContainer.val(), description: descriptionContainer.val(), author: authorContainer.val()};
            console.log("New article", newArticle);
            var self = this;

            $.ajax({
                type: 'post',
                url: this.rootUrl,
                data: JSON.stringify(newArticle),
                contentType: "application/json; charset=utf-8",
                traditional: true,
                success: function (scienceArticle) {
                    if (!scienceArticle) alert("Sorry! We are facing an issue.");

                    self.addScienceArticleToTable(scienceArticle);

                    //Clearing fields
                    titleContainer.val('')
                    descriptionContainer.val('')
                    authorContainer.val('')
                },
                error: function (error) {
                    console.log("Error on article save :", error);
                    alert("Sorry! We are facing an issue.");
                }
            });
        },
        addScienceArticleToTable: function (article) {
            var addArticleTemplate = $($('#template-add-article').html());

            addArticleTemplate.children('td.article-id').text(article.id);
            addArticleTemplate.children('td.article-title').text(article.title);
            addArticleTemplate.children('td.article-description').text(article.description);
            addArticleTemplate.children('td.article-author').text(article.author);
            addArticleTemplate.appendTo("#science-articles-tbody");
        },
        bindDetailsEditAndDeleteButtons: function () {
            $(document).on('click', '.single-article .details', this.showScienceArticleDetails.bind(this));
            $(document).on('click', '.single-article .edit', this.editScienceArticle.bind(this));
            $(document).on('click', '.single-article .delete', this.deleteScienceArticle.bind(this));
        },
        showScienceArticleDetails: function (event) {
            console.log('Event',event);
            var articleRow = $(event.target).closest('.single-article');
            var articleId = articleRow.find('.article-id').text();
            console.log("Requested to get article id",articleId);
            $.ajax({
                type: 'GET',
                url: (this.rootUrl + "/" + articleId),
                contentType: "application/json; charset=utf-8",
                traditional: true,
                success: function (article) {

                    console.log('Got article',article);

                    let articleString = `Id: ${article.id}\nTitle: ${article.title}\nDescription: ${article.description}`; //ES6 javascript templating feature

                    alert(articleString);
                },
                error: function (error) {
                    console.log("Error on article delete :", error);
                    alert("Sorry! We are facing an issue.");
                }
            });

        },
        editScienceArticle: function (event) {
            console.log('Event',event);
            var articleRow = $(event.target).closest('.single-article');
            var articleId = articleRow.find('.article-id').text();
            var title = articleRow.find('.article-title').text();
            var description = articleRow.find('.article-description').text();
            var author = articleRow.find('.article-author').text();

            var addContainer = $('#add-update-science-article'),
                idContainer = addContainer.find('.sa-id'),
                titleContainer = addContainer.find('.sa-title'),
                descriptionContainer = addContainer.find('.sa-description'),
                authorContainer = addContainer.find('.sa-author');

            idContainer.val(articleId);
            titleContainer.val(title)
            descriptionContainer.val(description)
            authorContainer.val(author)
            $('#btn-add-science-articles').hide();
            $('#btn-update-science-articles').show();
        },
        updateScienceArticle:function(){
            var addContainer = $('#add-update-science-article'),
                idContainer = addContainer.find('.sa-id'),
                titleContainer = addContainer.find('.sa-title'),
                descriptionContainer = addContainer.find('.sa-description'),
                authorContainer = addContainer.find('.sa-author');

            var updatedArticle = {title: titleContainer.val(), description: descriptionContainer.val(), author: authorContainer.val()};
            console.log("New article", updatedArticle);

            var self = this;
            $.ajax({
                type: 'PUT',
                url: (this.rootUrl + "/" + idContainer.val()),
                data: JSON.stringify(updatedArticle),
                contentType: "application/json; charset=utf-8",
                traditional: true,
                success: function (scienceArticle) {
                    if (!scienceArticle) alert("Sorry! We are facing an issue.");

                    self.loadScienceArticles();

                    //Clearing fields
                    idContainer.val('');
                    titleContainer.val('');
                    descriptionContainer.val('');
                    authorContainer.val('');

                    $('#btn-add-science-articles').show();
                    $('#btn-update-science-articles').hide();
                },
                error: function (error) {
                    console.log("Error on article save :", error);
                    alert("Sorry! We are facing an issue.");
                }
            });
        },
        deleteScienceArticle: function (event) {
            console.log('Event',event);
            var articleRow = $(event.target).closest('.single-article');
            var articleId = articleRow.find('.article-id').text();
            console.log("Requested to delete article id",articleId);

            $.ajax({
                type: 'delete',
                url: (this.rootUrl + "/" + articleId),
                contentType: "application/json; charset=utf-8",
                traditional: true,
                success: function (status) {

                    console.log('Delete status',status);

                    articleRow.remove();
                    alert("Article deleted successfully.");
                },
                error: function (error) {
                    console.log("Error on article delete :", error);
                    alert("Sorry! We are facing an issue.");
                }
            });
        }
    };

    scienceController.init();

});
