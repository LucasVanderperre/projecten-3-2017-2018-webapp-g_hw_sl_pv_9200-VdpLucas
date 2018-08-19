var mongoose = require('mongoose');

var PostSchema = new mongoose.Schema({
  datum: Date,
  titel: String,
  bericht: [String]
});
mongoose.model('Post', PostSchema);