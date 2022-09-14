const express = require('express');
const colors = require('colors');
require('dotenv').config();
const app = express();
const port = process.env.PORT || 5000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use('/api/users', require('./routes/userRoutes'));

app.listen(port, () => {
    console.log(`Server running on http://localhost:${port}`.yellow);

})
