function getTimeStamp(timeInMillis) {
    const now = new Date();
    now.setTime(timeInMillis);

    const expand = (number) => {
        if (number < 10) return '0' + number; else return number.toString();
    }

    const date = now.getDate();
    const minute = now.getMinutes();
    const hour = now.getHours().toString();
    const day = now.getDay();
    const month = now.getMonth() + 1;
    const year = now.getFullYear();

    return `${expand(hour)}:${expand(minute)} ${expand(day)}.${expand(month)}.${year}`;
}