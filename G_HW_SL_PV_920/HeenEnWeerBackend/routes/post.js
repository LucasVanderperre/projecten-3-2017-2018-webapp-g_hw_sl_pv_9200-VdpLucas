var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');

let Post = mongoose.model('Post');


/*GET post max 10*/
router.get("/", function (req, res, next) {
    var mysort = { datum: -1 };
    let query = Post.find().sort(mysort).limit(10);
    query.exec(function (err, posts) {
        if (err) return next(err);
        res.json(posts);
    });
});


/*POST Kost*/
router.post('/:userId', function (req, res, next) {
    if (!req.user.moderator)return next(new Error('Geen moderator!!'));
    let post = new Post(req.body);
    post.datum = new Date();
    post.save(function (err, rev) {
        if (err) { return next(err); }
        res.json(rev);
    });
});

router.param("userId", function (req, res, next, id) {
    let query = Gebruiker.findById(id);
    query.exec(function (err, usr) {
        if (err) {
            return next(err);
        }
        if (!usr) {
            return next(new Error("user not found " + id));
        }
        req.user = usr;
        return next();
    });
});

module.exports = router;
