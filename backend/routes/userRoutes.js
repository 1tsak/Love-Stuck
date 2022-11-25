const express = require('express');
const router = express.Router();

router.route('/').post((req, res) => {
    if(req.body){
        res.send(req.body)
    }
});

module.exports = router