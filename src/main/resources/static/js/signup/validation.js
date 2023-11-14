'use strict'

function idLength(value) {
    // 아이디 5~20자
    return value.length >= 5 && value.length <= 20;
}
function onlyNumbersAndEnglish(str) {
    // 영문 소문자, 숫자와 특수기호(_),(-)
    return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
}
function strongPassword (str) {
    // 최소 8글자 이상이면서, 알파벳과 숫자 및 특수문자(@$!%*#?&)가 하나 이상 포함
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
}
function isMatch(password1, password2) {
    // 비밀번호 확인
    return password1 === password2;
}
function isEmailValid(str) {
    // 이메일 양식 확인
    return /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(str);
}

function onlyNumbersAndEnglishAndKorean(str) {
    return /^[가-힣a-zA-Z0-9]{2,15}$/.test(str);
}

async function checkMemberId(memberId) {
    const request = await axios.post('/member/checkMemberId', null, {
        params: { memberId: memberId },
        responseType: 'text'
    });

    console.log(request.data);

    return request.data;
}

async function sendConfirmMail(mailTo) {
    console.log("sendConfirmMail");
    const request = await axios.get(`/member/sendConfirmMail?mailTo=${mailTo}`);

    return request.data;
}

async function matchConfirmKey(confirmKey) {
    console.log(confirmKey);
    const request = await axios.post('/member/matchConfirmKey', null, {
        params: { reqConfirmKey: confirmKey },
        responseType: 'text'
    });

    console.log("axios: " + request.data);
    return request.data;
}

async function removeConfirmKey() {
    const request = await axios.get('/member/removeConfirmKey');

    return request.data;
}