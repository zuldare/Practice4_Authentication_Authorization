const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const express = require('express');
const config = require('../config');
const usersRepository = require('../authRepo/userRepo');

let router = express.Router();

router.post('/register', async (req, res) => {

    let user = req.body;
    if (!(user.nickname && user.email && user.password)) {
        res.sendStatus(400);
    }

    user.password = bcrypt.hashSync(user.password, 8);
    user = await usersRepository.save(user)
    const token = await jwt.sign({ id: user._id }, config.secret, {
        expiresIn: 86400
    });

    res.status(200).send({ auth: true, token: token });
});

router.post('/login', async (req, res) => {

    const user = req.body;
    if (!(user.nickname && user.password)) {
        res.sendStatus(400);
    }

    const foundUser = await usersRepository.findByNickname(user.nickname);
    if (!foundUser[0]) res.status(401).send();

    const passwordIsValid = bcrypt.compareSync(user.password, foundUser[0].password);
    if (!passwordIsValid) res.status(401).send();

    const token = jwt.sign({ id: user._id }, config.secret, {
        expiresIn: 86400
    });

    res.status(200).send({ auth: true, token: token });

});

module.exports = router;
