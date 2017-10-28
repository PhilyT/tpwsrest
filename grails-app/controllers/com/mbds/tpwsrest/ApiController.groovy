package com.mbds.tpwsrest

import grails.converters.JSON
import grails.converters.XML

/**
 * Contolleur gérant les ressources book et library de l'api rest
 * @author Tom Phily
 */
class ApiController {

    /**
     * Action qui insère, retourne, met à jour ou suprime un livre en fonction de la méthode employée.
     * @return une instance de la classe Book en format JSON ou XML en fonction du header.
     */
    def book()
    {
        switch(request.getMethod()){
        /**
         * Service qui insère un livre puis retourne le livre inseré.
         */
            case "POST":
                if(!Library.get(params.library.id)){
                    render(status:400, text:"Cannot attach a book to a non existent library (${params.library.id})")
                    return
                }
                def bookInstance = new Book(params)
                if(bookInstance.save(flush:true))
                    switch(request.getHeader("Accept")){
                        case "application/json":
                            render bookInstance as JSON
                            break
                        case "application/xml":
                            render bookInstance as XML
                            break
                        default:
                            render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                            break
                    }
                else
                    response.status = 400
                break
        /**
         * Service qui retourne le livre identifié par l'id du paramètre de la requête.
         */
            case "GET":
                def bookInstance = Book.get(params.id)
                if(!bookInstance){
                    render(status:400, text:"Cannot return a non existent book (${params.id})")
                    return
                }
                switch(request.getHeader("Accept")){
                    case "application/json":
                        render bookInstance as JSON
                        break
                    case "application/xml":
                        render bookInstance as XML
                        break
                    default:
                        render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                        break
                }
                break
        /**
         * Service qui supprime le livre identifié par l'id du paramètre de la requête.
         */
            case "DELETE":
                def bookInstance = Book.get(params.id)
                if(!bookInstance){
                    render(status:400, text:"Cannot delete a non existent book (${params.id})")
                    return
                }
                bookInstance.delete(flush: true)
                render(status:200, text:"Book (${params.id}) supprimé")
                return
        /**
         * Service qui met à jour le livre avec les données en paramètres de la requête.
         */
            case "PUT":
                def bookInstance = Book.get(params.id)
                if(!bookInstance){
                    render(status:400, text:"Cannot update a non existent book (${params.id})")
                    return
                }
                if(params.name){
                    bookInstance.name = params.name
                }
                if(params.releaseDate){
                    bookInstance.releaseDate = Date.parse("yyyy-MM-dd hh:mm:ss.S",params.releaseDate)
                }
                if(params.isbn){
                    bookInstance.isbn = params.isbn
                }
                if(params.author){
                    bookInstance.author = params.author
                }
                if(bookInstance.save(flush:true)){
                    switch(request.getHeader("Accept")){
                        case "application/json":
                            render bookInstance as JSON
                            break
                        case "application/xml":
                            render bookInstance as XML
                            break
                        default:
                            render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                            break
                    }
                }else{
                    response.status = 400
                }
                break
            default:
                response.status = 405
                break
        }
    }

    /**
     * Action du service qui retourne la liste des livres
     * @return la liste des livres sous formet JSON ou XML en fonction du header.
     */
    def books(){
        if(request.getMethod().equals("GET")){
            switch(request.getHeader("Accept")){
                case "application/json":
                    render Book.getAll() as JSON
                    break
                case "application/xml":
                    render Book.getAll() as XML
                    break
                default:
                    render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                    break
            }
        }else{
            response.status = 405
        }
    }

    /**
     * Action qui insère, retourne, met à jour ou suprime une bilbiothèque en fonction de la méthode employée.
     * @return une instance de la classe bibliothèque en format JSON ou XML en fonction du header.
     */
    def library()
    {
        switch(request.getMethod()){
        /**
         * Service qui insère une bibliothèque puis retourne la bibliothèque inserée.
         */
            case "POST":
                def libraryInstance = new Library(params)
                if(libraryInstance.save(flush:true))
                    switch(request.getHeader("Accept")){
                        case "application/json":
                            render libraryInstance as JSON
                            break
                        case "application/xml":
                            render libraryInstance as XML
                            break
                        default:
                            render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                            break
                    }
                else
                    response.status = 400
                break
        /**
         * Service qui retourne la bibliothèque identifiée par l'id du paramètre de la requête.
         */
            case "GET":
                def libraryInstance = Library.get(params.id)
                if(!libraryInstance){
                    render(status:400, text:"Cannot return a non existent library (${params.id})")
                    return
                }
                switch(request.getHeader("Accept")){
                    case "application/json":
                        render libraryInstance as JSON
                        break
                    case "application/xml":
                        render libraryInstance as XML
                        break
                    default:
                        render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                        break
                }
                break
        /**
         * Service qui supprime la bibliothèque identifiée par l'id du paramètre de la requête.
         */
            case "DELETE":
                def libraryInstance = Library.get(params.id)
                if(!libraryInstance){
                    render(status:400, text:"Cannot delete a non existent library (${params.id})")
                    return
                }
                libraryInstance.delete(flush: true)
                render(status:200, text:"Library (${params.id}) supprimé")
                return
        /**
         * Service qui met à jour la bibliothèque avec les données en paramètres de la requête.
         */
            case "PUT":
                def libraryInstance = Library.get(params.id)
                if(!libraryInstance){
                    render(status:400, text:"Cannot update a non existent library (${params.id})")
                    return
                }
                if(params.name){
                    libraryInstance.name = params.name
                }
                if(params.address){
                    libraryInstance.address = params.address
                }
                if(params.yearCreated){
                    libraryInstance.yearCreated = Integer.parseInt(params.yearCreated)
                }
                if(libraryInstance.save(flush:true)){
                    switch(request.getHeader("Accept")){
                        case "application/json":
                            render libraryInstance as JSON
                            break
                        case "application/xml":
                            render libraryInstance as XML
                            break
                        default:
                            render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                            break
                    }
                }else{
                    response.status = 400
                }
                break
            default:
                response.status = 405
                break
        }
    }

    /**
     * Action du service qui retourne la liste des bibliothèques
     * @return la liste des bibliothèques sous formet JSON ou XML en fonction du header.
     */
    def libraries(){
        if(request.getMethod().equals("GET")) {
            switch(request.getHeader("Accept")){
                case "application/json":
                    render Library.getAll() as JSON
                    break
                case "application/xml":
                    render Library.getAll() as XML
                    break
                default:
                    render(status:400, text:"Cannot return type : " + request.getHeader("Accept"))
                    break
            }
        }else{
            response.status = 405
        }
    }
}
