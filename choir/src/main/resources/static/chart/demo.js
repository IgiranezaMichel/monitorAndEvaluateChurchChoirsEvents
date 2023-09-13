var ctx = document.getElementById('employee').getContext('2d');
var labels = ['Mike','Joel', 'Murisa Jean', 'Pendo', 'Joella', 'Yvon '];
var data = [4,1, 9, 3, 7, 4];
var colors = ['#FF6384', '#6A2EB', '#FFCE56', '#4BC0C0', '#9966FF','#FF6324','#FF63-124'];

var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: 'Activer users',
            data: data,
            backgroundColor: colors, 
            borderColor: 'rgba(75, 192, 192, 1)', 
            borderWidth: 1 
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true 
            }
        }
    }
});
