Vue.createApp({
    data() {
        return {
            dataClient: {},
            clientAccounts: [],
            clientLoans: [],
            allTransactions:[],
        }
    },

    created() {
        axios.get(`/api/clients/current`)
            .then(json => {
                this.dataClient = json.data
                this.clientAccounts = json.data.accounts.sort((a, b) => { return a.id - b.id })
                this.clientLoans = json.data.loans.sort((a, b) => { return a.id - b.id })
                json.data.accounts.forEach(account => account.transactions.forEach(transaction=> this.allTransactions.push(transaction)))
                console.log(this.allTransactions)
            })
    },

    methods: {
        redirect(id) {
            window.location = `http://localhost:8080/web/account.html?id=${id}`
        },
        logOut(){
            axios.post('/api/logout')
            .then(console.log)
            .then(response => setTimeout(function(){
                window.location.href='./index.html'
            },1000))
        },
        addAccount(){
            axios.post('/api/clients/current/accounts')
            .then(response=> window.location.href = '/web/accounts.html')
            .catch(error=> console.log(error.message.status))
        },
        applyLoan(){
            window.location.href='./loan-application.html'
        },
    },

    computed: {
        
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