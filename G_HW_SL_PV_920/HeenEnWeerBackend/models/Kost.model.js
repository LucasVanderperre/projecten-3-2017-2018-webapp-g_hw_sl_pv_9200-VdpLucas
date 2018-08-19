var mongoose = require('mongoose');


var KostSchema = new mongoose.Schema({
  naam: String,
  aanmaker: String,
  aanmakerId: String,
  betrokkenPersonen: [String],
  datum: Date,
  kost: Number,
  beschrijving: String,
  categorie: String,
  
  uitzonderlijk: Boolean,
  goedgekeurd: Boolean


});
mongoose.model('Kost', KostSchema);

