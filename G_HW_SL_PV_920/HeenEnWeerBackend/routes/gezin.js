var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Gezin = mongoose.model('Gezin');
let Gesprek = mongoose.model('Gesprek');
let jwt = require('express-jwt');
let auth = jwt({ secret: process.env.SECRET, userProperty: 'payload' });
/**
 * huidig gezin ophalen van ingelogde gebruiker
 */
router.get('/:gebruikerId', auth, function (req, res) {
  Gebruiker.findById(req.params.gebruikerId, function (err, gebruiker) {
    if (err) { return next(err) };
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) { return next(err) };
      //let gesprekken = [];
      //gezin.gesprekken.forEach(function(gesprekId) {
      //  Gesprek.findById(gesprekId,
      //    function(err, gesprek) {
      //      gesprekken.push(gesprek);
      //    }).populate("berichten").populate("deelnemers");
      //});
      //gezin.gesprekken = gesprekken;
      console.log(gezin);
      res.json(gezin);
    }).populate("gesprekken").populate("gezinsLeden").populate("activiteiten").populate("kosten").populate("ouders");
  });
});

/**
 * Alle gezinnen ophalen van ingelogde gebruiker
 */
//router.get('/:gebruikerId/gezinnen', function (req, res) {
//  let gezinnen = [];
//  Gebruiker.findById(req.params.gebruikerId, function (err, gebruiker) {
//    if (err) { return next(err) };
//    gebruiker.gezinnenIdLijst.forEach(id => {
//      let gezin = Gezin.findById(id);
//      gezinnen.push(gezin);
//    });
//    res.json(gezinnen);
//  });
//});


/**
 * Nieuw gezin maken
 */
router.post('/nieuw/:userId', function (req, res) {
  let gezin = new Gezin(req.body.gezin);
  gezin.naam = req.body.gezin._naam;
  console.log(gezin);
  //gezin.gezinsLeden = req.body.gezin._gezinsleden;
  let gesprek;
  req.body.gezin._gesprekken.forEach(function (value) {
    gesprek = new Gesprek(value);
    gesprek.naam = value._naam;
    gesprek.deelnemers = value._deelnemers;
    console.log("Gesprek: " + gesprek);
    gesprek.save(function (err) {
      if (err) { return next(err); }
    });
    gezin.gesprekken.push(gesprek._id);
  });
  req.body.gezin._gezinsleden.forEach(function (value) {
    let user;

    if (value.username === "") {
      user = new Gebruiker(value);
      user.voornaam = value.voornaam;
      user.familienaam = value.familienaam;
      user.kleur = value.kleur;
      user.username = value.username;
    } else {
      user = value;
    }
    user.huidigGezinId = gezin._id;
    user.gezinnenIdLijst.push(gezin._id);
    gezin.gezinsLeden.push(user._id);
    //if (value.username.length === 0) {
    //  console.log("length");
    //}   

    if (value.username == "") {
      console.log("2 == tekens");
    }


    if (value.username === "") {
      console.log("3 === tekens");
    }

    if (value.username.length === 0) {

      console.log("in if in foreach");
      console.log(user);
      user.save(function (err, user) {
      });
    } else {
      Gebruiker.findOneAndUpdate({ _id: user._id }, user, function (err, doc) {
        //if (err) return res.send(500, { error: err });
        //return res.send("succesfully saved");
      });
    }

    //Gebruiker.findById(value._id, function (user, err) {
    //  user.huidigGezinId = gezin._id;
    //  user.gezinnenIdLijst.push(gezin._id);
    //  gezin.gezinsLeden.push(user._id);
    //  console.log("gezinsliden in gezin: " + gezin.gezinsLeden);
  });
  console.log("gezinsliden in gezin: " + gezin.gezinsLeden);

  gezin.save(function (err) {
    if (err) { return next(err); }
    console.log("Nieuw Gezin: ");
    console.log(gezin);
    res.json(gezin);
  });


  //req.body.gezin._gesprekken.forEach(g => {
  //  console("Gesprek: ");
  //  console.log(g);
  //});
  //Gebruiker.findById(req.params.userId, function (err, gebruiker) {
  //  if (err) { return next(err) };
  //  gebruiker.huidigGezinId = gezin._id;
  //  gebruiker.gezinnenIdLijst.push(gezin._id);
  //  gezin.gezinsLeden.push(gebruiker._id);
  //  gebruiker.save(function (err) {
  //    if (err) { return next(err); }
  //  });
  //  gezin.save(function (err) {
  //    if (err) { return next(err); }
  //    console.log("Nieuw Gezin: ");
  //    console.log(gezin);
  //    res.json(gezin);
  //  });
  //});
});

module.exports = router;
