const mongoose = require('mongoose')
const connectDB = async () => {
    try
    {
        const conn = await mongoose.connect(process.env.MONGO_URI)
        if (process.env.DEV == 'true') {
            console.log(`MongoDB Connected ${conn.connection.host}`.cyan.underline)
        }else{
            console.log("shit");
        }

    } catch (err)
    {
        console.log(err)
        process.exit(1)

    }
}

module.exports = connectDB