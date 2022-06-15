/*
(issue BLOG-58) Begin JS changes to animate notification messages
 */

$(function() {
    $('#messages li').click(function() {
        $(this).fadeOut();
    });
    setTimeout(function() {
        $('#messages li.info').fadeOut();
    }, 3000);
});

/*
(issue BLOG-58) End JS changes
 */

