Vue.createApp({
    data() {
        return {
            //Login
            email: "",
            password: "",
            //Register
            newFirstName:"",
            newLastName:"",
            newEmail:"",
            newPassword:"",
        }
    },

    created() {

    },

    methods:{
        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>
                setTimeout(function(){
                window.location.href='./accounts.html'
            },1000))
            
        },

        register(){
            axios.post('/api/clients',`firstName=${this.newFirstName}&lastName=${this.newLastName}&email=${this.newEmail}&password=${this.newPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => 
                axios.post('/api/login',`email=${this.newEmail}&password=${this.newPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            )
            .then(response =>
                setTimeout(function(){
                window.location.href='./accounts.html'
            },1000))
            .catch(error=> console.log(error))
        },
    },

    computed:{

    },
}).mount('#app')