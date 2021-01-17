const express = require('express');
const database = require('./database.js');
const booksRouter = require('./routes/bookRouter.js');
const usersRouter = require('./routes/userRouter.js');


let fs = require('fs');
let https = require('https');

const app = express();

//Convert json bodies to JavaScript object
app.use(express.json());
app.use('/api/v1/books', booksRouter);
app.use('/api/v1/users', usersRouter);

app.use(authRouter);


database.connect();



https.createServer({
    key: fs.readFileSync('./certs/server.key', 'utf8'),
    cert: fs.readFileSync('./certs/server.cert', 'utf8')
}, app).listen(3443, () => {
    console.log("Https server started in port 3443");
});


process.on('SIGINT', () => {
    database.disconnect();
    console.log('Process terminated');
    process.exit(0);
});


