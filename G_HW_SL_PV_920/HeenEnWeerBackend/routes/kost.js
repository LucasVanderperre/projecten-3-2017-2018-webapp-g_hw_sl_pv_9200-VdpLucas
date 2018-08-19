var express = require('express');
var router = express.Router();
let mongoose = require('mongoose');
let Gebruiker = mongoose.model('Gebruiker');
let Kost = mongoose.model('Kost');
let Gezin = mongoose.model('Gezin');

/*GET kost van huidiggezin*/
router.get('/:userId', function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      res.json(gezin.kosten.sort({datum:-1}));
    }).populate("kosten");
  });
});
/*GET kosten van gezin van een bepaalde maand*/
router.get('/:userId/:maand/:jaar', function (req, res, next) {
  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      var kosten = gezin.kosten.filter(kst => kst.datum.getMonth() === parseInt(req.params.maand) - 1 
      && kst.datum.getFullYear() === parseInt(req.params.jaar)).sort({datum:-1});
      //kst.datum.getMonth() === req.params.maand);
      console.log(kosten);

      res.json(kosten);
    }).populate("kosten");
  });
});
/* var kosten ;
gezin.kosten.forEach(element => {
   console.log(element.datum.getMonth(), element.datum.getFullYear());
   console.log(req.params.maand, req.params.jaar);
   if(element.datum.getMonth() === req.params.maand && element.datum.getFullYear() === req.params.jaar ){
     kosten.push(element);
   }
 });
res.json(kosten);
}).populate("kosten");
});
});*/

/*GET kosten van maand
router.get('/:userId/:maand/:jaar', function (req, res, next) {
  var startTime = new Date(req.param.jaar + '-' + req.param.maand + '-00T00:00:00');
  var endTime = new Date(req.param.jaar + '-' + req.param.maand + 1 + '-00T00:00:00');

  Gebruiker.findById(req.params.userId, function (err, gebruiker) {
    if (err) return next(err);
    Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
      if (err) return next(err);
      if (!gezin)
        return next(new Error('not found ' + gebruiker.huidigGezinId));
      Gezin.kosten.find({
        datum: {
          $gt: startTime,
          $lt: endTime
        }
      })
      res.json(gezin.kosten);
    }).populate("kosten");
  });
});

/*POST Kost*/
router.post('/:userId', function (req, res, next) {
  console.log(req.body);
  if (req.body.kost.kost == null) {
    var kost = new Kost(req.body);
  } else {
    var kost = new Kost(req.body.kost);
  }
  kost.goedgekeurd = false;
  kost.save(function (err, post) {
    if (err) { return next(err); }
    Gebruiker.findById(req.params.userId, function (err, gebruiker) {
      if (err) return next(err);
      Gezin.findById(gebruiker.huidigGezinId, function (err, gzn) {
        if (err) return next(err);
        gzn.kosten.push(post._id);
        gzn.save(function (err, rec) {
          if (err) return next(err);
          res.json(post);
        });
      });
    });
  });
});

//keur kost goed
router.put('/keurgoed/:userId/:kostId', function (req, res, next) {
  Kost.findById(req.params.kostId, function (err, kost) {
    if (err) return next(err);
    if (kost === null) {
      return next(new Error("Kost not found " + req.param.kostId));
    }
    if (kost.aanmakerId === req.param.userId) {
      return next(new Error("User is aanmaker " + req.param.kostId));
    }
    kost.goedgekeurd = true;
    kost.save(function (err, updatedKost) {
      if (err) return handleError(err);
      res.json(updatedKost);
    });
  });
});
/*
  let query = Kost.findById(req.param.kostId);
  query.exec(function (err, kost) {
    if (err) {
      return next(err);
    }
    if (kost === null) {
      return next(new Error("Kost not found " + req.param.kostId));
    }
    if (kost.aanmakerId === req.param.userId) {
      return next(new Error("User is aanmaker " + req.param.kostId));
    }
    console.log(kost);
    kost.goedgekeurd = true;
    kost.save(function (err, updatedKost) {
      if (err) return handleError(err);
      res.json(updatedKost);
    });
  });
});
*/
/*DELETE Kost*/
router.delete('/:userId/:kostId', function (req, res, next) {
  //Gebruiker.findById(req.params.userId, function (err, gebruiker) {
  //  if (err) return next(err);
  //  Gezin.findById(gebruiker.huidigGezinId, function (err, gezin) {
  //    if (err) return next(err);
  //    gezin.removeKost(req.params.kostId);
  //    gezin.save(function (err, post) {
  //      if (err) { return next(err); }
  //    });
  //  }).populate("kosten");
  //});
  Kost.findOneAndRemove({ _id: req.params.kostId }, function (err, docs) {
    if (err) { res.json(err); }
    res.json(req.params.kostId);
  });
});

/*Wijzig Kost*/
router.put('/:userId/:kostId',/* auth,*/ function (req, res, next) {
  Kost.findByIdAndUpdate(req.params.kostId, req.body.kost, function (err, post) {
    if (err) return next(err);
    res.json(req.body.kost);
  });
});

/*GET Activiteit MET ID*/
router.put('/:userId/:activiteitId', function (req, res, next) {
  Activiteit.findById(req.params.activiteitId,
    function (err, activiteit) {
      if (err) return next(err);
      if (!activiteit)
        return next(new Error('not found ' + req.params.activiteit));
      res.json(activiteit);
    });
});




module.exports = router;
