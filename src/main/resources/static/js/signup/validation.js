'use strict'

function isLength(str, a, b) {
    // a ~ b 자리까지
    return str.length >= a && str.length <= b;
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
    return /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(str);
}

function onlyNumbersAndEnglishAndKorean(str) {
    // 한글, 영문자, 숫자
    return /^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$/.test(str);
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
    try {
        console.log("sendConfirmMail");
        const request = await axios.get(`/member/sendConfirmMail?mailTo=${mailTo}`);
        return request.data;
    } catch (error) {
        console.error("Error sending confirmation mail:", error);
        // 오류 처리를 추가할 수 있음
        throw error; // 오류를 상위로 다시 던져서 다른 곳에서도 처리할 수 있도록 함
    }
}

// async function sendConfirmMail(mailTo) {
//     console.log("sendConfirmMail");
//     const request = await axios.get(`/member/sendConfirmMail?mailTo=${mailTo}`);
//
//     return request.data;
// }

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