Vue.createApp({
    data(){
        return{
            dataClient:{},
            typeCard:"CREDIT",
            colorCard:"SILVER"
    }},

    created() {
        axios.get(`http://localhost:8080/api/clients/current`)
            .then(json => {
                this.dataClient = json.data
                this.dataCards = json.data.cards
            })

    },

    methods:{
        getCurrentName(){
            let fullName = this.dataClient.firstName + " " +this.dataClient.lastName
            return fullName
        },

        getCreateDate(){
            const date = new Date()
            console.log(date)
            const dateYear = date.getFullYear()
            const getTwoDigits = dateYear.toString().substring(2)
            return (date.getMonth() + 1) + "/" + getTwoDigits 
        },
        getExpiresDate(){
            const date = new Date()
            console.log(date)
            const dateYear = date.getFullYear() + 5
            const getTwoDigits = dateYear.toString().substring(2)
            return (date.getMonth() + 1) + "/" + getTwoDigits 
        },
        createCard(){
            axios.post('/api/clients/current/cards',`color=${this.colorCard}&type=${this.typeCard}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response=> console.log("Your card has been created"))
            .then(setTimeout(function(){
                window.location.href='./cards.html'
            },1000))
        },
                logOut(){
                    axios.post('/api/logout')
                    .then(response => setTimeout(function(){
                        window.location.href='./index.html'
                    },1000))
                },
    },

}).mount("#app")

document.addEventListener("DOMContentLoaded", function(event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) =>{
    const toggle = document.getElementById(toggleId),
    nav = document.getElementById(navId),
    bodypd = document.getElementById(bodyId),
    headerpd = document.getElementById(headerId)
    
    // Validate that all variables exist
    if(toggle && nav && bodypd && headerpd){
    toggle.addEventListener('click', ()=>{
    // show navbar
    nav.classList.toggle('show')
    // change icon
    toggle.classList.toggle('bx-x')
    toggle.classList.toggle('text-light')
    // add padding to body
    bodypd.classList.toggle('body-pd')
    // add padding to header
    headerpd.classList.toggle('body-pd')
    })
    }
    }
    
    showNavbar('header-toggle','nav-bar','body-pd','header')
    
    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')
    
    function colorLink(){
    if(linkColor){
    linkColor.forEach(l=> l.classList.remove('active'))
    this.classList.add('active')
    }
    }
    linkColor.forEach(l=> l.addEventListener('click', colorLink))
    
    // Your code to run since DOM is loaded and ready
    });