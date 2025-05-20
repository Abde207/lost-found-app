const i18n = {
  ar: {
    appTitle: "CampusFinder",
    lost: "المفقودات",
    found: "الموجودات",
    reportLost: "أعلنت عن فقدان",
    reportFound: "أعلنت عن إيجاد",
    login: "تسجيل الدخول",
    reg: "تسجيل مستخدم جديد",
    send: "أرسل",
    close: "إغلاق",
    loading: "جاري التحميل...",
    uploadFailed: "فشل في رفع الصورة",
    submissionSuccess: "تم الإرسال بنجاح",
    submissionError: "حدث خطأ أثناء الإرسال",
    loginFirst: "يرجى تسجيل الدخول أولاً",
    lostTitle: "أعلن عن شي مفقود",
    foundTitle: "أعلن عن شي تم إيجاده",
    loginSuccess: "تم تسجيل الدخول",
    logout: "تسجيل الخروج"
  },
  en: {
    appTitle: "CampusFinder",
    lost: "Lost Items",
    found: "Found Items",
    reportLost: "Report Lost",
    reportFound: "Report Found",
    login: "Login",
    reg: "Register",
    send: "Send",
    close: "Close",
    loading: "Loading...",
    uploadFailed: "Image upload failed",
    submissionSuccess: "Submitted successfully",
    submissionError: "An error occurred",
    loginFirst: "Please log in first",
    lostTitle: "Report a Lost Item",
    foundTitle: "Report a Found Item",
    loginSuccess: "login Success",
    logout: "logout"
  }
};

let currentLang = 'ar';

function setLanguage(lang) {
  currentLang = lang;
  document.documentElement.lang = lang;
  document.documentElement.dir = lang === 'ar' ? 'rtl' : 'ltr';

  document.querySelectorAll('[data-i18n]').forEach(el => {
    const key = el.getAttribute('data-i18n');
    if (i18n[lang][key]) {
      el.textContent = i18n[lang][key];
    }
  });
}
