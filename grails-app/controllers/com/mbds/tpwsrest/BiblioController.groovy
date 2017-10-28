package com.mbds.tpwsrest

import grails.converters.JSON
import grails.converters.XML

/**
 *
 */
class BiblioController {
    /**
     *
     * @param libraryInstance
     * @return
     */
    def books(Library libraryInstance){
        if(request.getMethod()=="GET"){
            if(!libraryInstance){
                render(status:400, text:"Cannot return books of a null library")
                return
            }
            switch(request.getHeader("Accept")){
                case "application/json":
                    render libraryInstance.books as JSON
                    break
                case "application/xml":
                    render libraryInstance.books as XML
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
     *
     * @param libraryInstance
     * @return
     */
    def book(Library libraryInstance) {
        if(!libraryInstance){
            render(status:400, text:"Cannot get, post, update or delete a book of a null library")
            return
        }
        switch(request.getMethod()){
        /**
         * Service qui insère un livre dans la bibliothèque donnée puis retourne le livre inseré.
         */
            case "POST":
                def bookInstance = new Book(params)
                if(libraryInstance.addToBooks(bookInstance).save(flush:true))
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
         * Service qui retourne le livre de la bibliothèque identifié par l'id en paramètre de la requête.
         */
            case "GET":
                def bookInstance = libraryInstance.books.find({it.id == Integer.parseInt(params.book.id)})
                if(!bookInstance){
                    render(status:400, text:"Book (${params.book.id}) is not existent in library ${libraryInstance.id}")
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
         * Service qui supprime le livre de la biliothèque identifié par l'id en paramètre de la requête.
         */
            case "DELETE":
                def bookInstance = libraryInstance.books.find({it.id == Integer.parseInt(params.book.id)})
                if(!bookInstance){
                    render(status:400, text:"Book (${params.book.id}) is not existent in library ${libraryInstance.id}")
                    return
                }
                libraryInstance.books.remove(bookInstance)
                bookInstance.delete(flush: true)
                render(status:200, text:"Book (${params.book.id}) supprimé")
                return
        /**
         * Service qui met à jour le livre de la bibliothèqye avec les données en paramètres de la requête.
         */
            case "PUT":
                def bookInstance = libraryInstance.books.find({it.id == Integer.parseInt(params.book.id)})
                if(!bookInstance){
                    render(status:400, text:"Book (${params.book.id}) is not existent in library ${libraryInstance.id}")
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
}
