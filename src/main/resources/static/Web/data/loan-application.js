Vue.createApp({
    data() {
        return {
            dataLoans:[],
            clientAccounts:[],
            loanPayments:[],
            loanMaxAmount:0,

            loanId:0,
            amount:1,
            paymentSelected:0,
            numberAccount:"",


        }
    },

    created() {
        axios.get(`/api/loans`)
            .then(json => {
                this.dataLoans = json.data
                console.log(this.dataLoans);
            })
            axios.get(`/api/clients/current`)
            .then(json => {
                this.clientAccounts = json.data.accounts
            })
    },

    methods: {
        logOut(){
            axios.post('/api/logout')
            .then(console.log)
            .then(response => setTimeout(function(){
                window.location.href='./index.html'
            },1000))
        },

        loanSelected(loan){
            this.loanId = loan.id
            this.loanPayments = loan.payments
            this.loanMaxAmount = loan.maxAmount
            console.log(this.loanId)
            console.log(this.loanPayments)
        },

        applyLoan(){
            let object = {
                loanId: this.loanId,
                amount: this.amount,
                payment: this.paymentSelected,
                numberAccount:this.numberAccount
            }
            axios.post('/api/loans', object)
            .then(response=> window.location.href='./accounts.html ')
        },
    },

    computed: {
        totalPaymentForMonth(){
            let result = (this.amount * 1.2)/this.paymentSelected
            return result.toFixed(2)
        },
        totalPayment(){
            let result = (this.amount * 1.2)
            return result.toFixed(2)
        },
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