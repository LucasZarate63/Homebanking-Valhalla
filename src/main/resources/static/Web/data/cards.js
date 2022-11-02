Vue.createApp({
    data() {
        return {
            dataClient: {},
            dataCards:[],
            

        }
    },

    created() {
        axios.get(`/api/clients/current`)
            .then(json => {
                this.dataClient = json.data
                this.dataCards = json.data.cards
            })
            
    },

    methods: {

        getExpiresDate(dateImput){
            const date = new Date(dateImput)
            console.log(date)
            const dateYear = date.getFullYear()
            const getTwoDigits = dateYear.toString().substring(2)
            return (date.getMonth() + 1) + "/" + getTwoDigits 
        },
        logOut(){
            axios.post('/api/logout')
            .then(console.log)
            .then(response => setTimeout(function(){
                window.location.href='./index.html'
            },1000))
        },

        redirect(){
            setTimeout(function(){
                window.location.href='./create-card.html'
            },1000)
        },

        visualCardNumber(number){
            console.log(number)
            let part1 = number.toString().slice(0,4)
            let part2 = number.toString().slice(4,8)
            let part3 = number.toString().slice(8,12)
            let part4 = number.toString().slice(12,16)
            let result = part1 + "-" +part2 + "-" +part3 + "-" +part4
            return result
        },
    },

}).mount('#app')





document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if (toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
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

    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink() {
        if (linkColor) {
            linkColor.forEach(l => l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready
});