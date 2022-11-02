Vue.createApp({
    data() {
        return {
            urlParams:{},
            id:"",
            dataAccount:{},
            dataTransactions:[],
        }
    },

    created() {
        this.urlParams = new URLSearchParams(window.location.search)
        this.id = this.urlParams.get('id')

        axios.get(`http://localhost:8080/api/accounts/${this.id}`)
            .then(json => {
                this.dataAccount = json.data
                this.dataTransactions = json.data.transactions.sort((a,b) => {return a.id- b.id})
                console.log()
            })
    },

    methods: {
        logOut(){
            axios.post('/api/logout')
            .then(console.log)
            .then(response => setTimeout(function(){
                window.location.href='./index.html'
            },1000))
        }
    },

    computed: {
    },
}).mount('#app')

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