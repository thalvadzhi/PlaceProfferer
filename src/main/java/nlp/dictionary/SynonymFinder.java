package nlp.dictionary;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetNode;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.data.list.PointerTargetTreeNode;
import net.sf.extjwnl.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thalvadzhiev on 1/30/18.
 */
public class SynonymFinder {
    private Dictionary dict;

     public SynonymFinder(){
         try {

             dict = Dictionary.getDefaultResourceInstance();
         } catch (JWNLException e) {
             e.printStackTrace();
         }
     }

     public List<String> findSynonymsOf(String posTag, String word){
         HashSet<String> synonyms = new HashSet<>();
         try {
             POS pos = posTagToPOS(posTag);
             IndexWord indexWord = this.dict.getIndexWord(pos, word);

             for (int i = 0; i < indexWord.getSenses().size(); i++) {
                 PointerTargetTree synonymNodes = PointerUtils.getSynonymTree(indexWord.getSenses().get(i), 3);
                 for(PointerTargetNodeList nodeList : synonymNodes.toList()){
                     for(PointerTargetNode node : nodeList){
                         synonyms.addAll(node.getSynset().getWords().stream().map(Word::getLemma)
                                 .collect(Collectors.toList()));
                     }
                 }
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return new ArrayList<>(synonyms);
     }

     private POS posTagToPOS(String posTag) throws Exception {
         if(posTag.matches("V.*")){
             return POS.VERB;
         }else if(posTag.matches("N.*")){
             return POS.NOUN;
         }else if(posTag.matches("J.*")){
             return POS.ADJECTIVE;
         }
         throw new Exception("Don't know how to handle this pos tag");
     }
}
