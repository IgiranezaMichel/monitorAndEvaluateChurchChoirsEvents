
const currentDate1 = new Date();
const addLeadingZero1 = value => (value < 10 ? `0${value}` : value);
const year1 = currentDate.getFullYear();
const month1 = addLeadingZero(currentDate.getMonth() + 1); 
const day1 = addLeadingZero(currentDate.getDate());
const formattedDate1 = `${day}-${month}-${year}`;
document.getElementById('printeddate').innerHTML=formattedDate1;

  const currentDate = new Date();
  const addLeadingZero = value => (value < 10 ? `0${value}` : value);
  const year = currentDate.getFullYear();
  const month = addLeadingZero(currentDate.getMonth() + 1); 
  const day = addLeadingZero(currentDate.getDate());
  const formattedDate = `${day}-${month}-${year}`;
  document.getElementById('date').innerHTML =formattedDate;
  document.getElementById('printeddate').innerHTML=formattedDate;
