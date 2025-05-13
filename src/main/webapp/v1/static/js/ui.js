$(function(){
    // Accodian(dl, dt, dd)
	if ($('.ui-acc').length > 0) { accMenu();}
});

document.addEventListener('DOMContentLoaded', function () {
  const swiper = new Swiper('.swiper', {
    speed: 400,
    spaceBetween: 100,
	autoplay: {
	    delay: 5000,
	    disableOnInteraction: false
	  },
	loop: true,
	scrollbar: {
       el: ".swiper-scrollbar",
       hide: true,
	},
  });
});

// Accodian(dl, dt, dd)
function accMenu(){
	const ah = $('.ui-acc dt');
    const ab = $('.ui-acc dd');
		
	ab.slideUp(0);
	ah.on('click', function (){
		let accDl = $(this).parent('dl');
		let accDt = $(this);
		let accDd = $(this).next('dd');
		
        console.log('click')
        
		if(accDl.hasClass('on')){
			accDl.removeClass('on');
			accDl.siblings('dl').removeClass('on');
			accDd.slideUp(0);

            console.log('remove class on')
		}else{
			accDl.addClass('on');
			accDd.slideDown(0);
			accDl.siblings('dl').removeClass('on');
			accDl.siblings().find('dd').slideUp(0);

            console.log('add Class on')
		}
	});
}
