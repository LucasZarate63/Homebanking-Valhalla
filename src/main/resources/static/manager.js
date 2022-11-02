Vue.createApp({
    data() {
        return {
            dataLoad:{},
            clients:[],
            // 
            newClient:{},
            newFirstName:"",
            newLastName:"",
            newEmail:"",
            // 
            clientSelected:{},
            // 
            firstNameEdit:"",
            lastNameEdit:"",
            emailEdit:"",
        }
    },

    created(){
        axios.get(`http://localhost:8080/api/clients`)
        .then(json=>{
            this.dataLoad = json.data
            this.clients = json.data
        })
    },

    methods: {
        addClient(){
            if (this.newFirstName != "" && this.newLastName != "" && this.newEmail != "" && this.newEmail.includes("@") && this.newEmail.includes(".")){
                this.newClient ={
                    firstName: this.newFirstName,
                    lastName: this.newLastName,
                    email: this.newEmail
                }
                axios.post(`http://localhost:8080/rest/clients/`,this.newClient)
                location.reload()
            }
        },

        takeDataClient(client){
            this.clientSelected = client
        },

        editClient(){
            this.firstNameEdit = document.querySelector("#nameEdit").value
            this.lastNameEdit = document.querySelector("#lastNameEdit").value
            this.emailEdit = document.querySelector("#emailEdit").value
            if(this.firstNameEdit != "" && this.lastNameEdit != "" && this.emailEdit != "" && this.emailEdit.includes("@") && this.emailEdit.includes(".")){
            axios.patch(this.clientSelected._links.client.href, {
                firstName: this.firstNameEdit,
                lastName: this.lastNameEdit,
                email: this.emailEdit,
            })
            location.reload()
        }
        },

        deleteClient(){
            axios.delete(this.clientSelected._links.client.href)
            location.reload()
        },

    },
}).mount('#app')