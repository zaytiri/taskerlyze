// Work in progress, not usable.
// The idea is to try to have a draggable list of titled panes and at the same time moving the titled panes order ids between each other.
// This code works for draggable purposes but I can't have the target titled pane to switch order ids with and having all visually smooth.
// The idea is to make the equivalent to tabdragPolicy for tabs. The exactly same effect.
// to be used like this: Draggable.Nature draggable = new Draggable.Nature(comp);
//            draggable.setParentPane(mainTasks);

package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generalised implementation of 'Draggability' of a {@link Node}. The Draggable class is used as a 'namespace' for the internal
 * class/interfaces/enum.
 *
 * @author phill
 */
public class Draggable {
    public enum Event {
        None, DragStart, Drag, DragEnd
    }

    /**
     * Marker for an entity that has draggable nature.
     *
     * @author phill
     */
    public interface Interface {
        Draggable.Nature getDraggableNature();
    }

    public interface Listener {
        void accept(Nature draggableNature, Event dragEvent);
    }

    /**
     * Class that encapsulates the draggable nature of a node.
     * <ul>
     * <li>EventNode: the event that receives the drag events</li>
     * <li>One or more DragNodes: that move in response to the drag events. The EventNode is usually (but not always) a
     * DragNode</li>
     * <li>Listeners: listen for the drag events</li>
     * </ul>
     *
     * @author phill
     */
    public static final class Nature implements EventHandler<MouseEvent> {
        private final boolean enabled = true;
        private final Node eventNode;
        private final List<Node> dragNodes = new ArrayList<>();
        private final List<Listener> dragListeners = new ArrayList<>();
        private double lastMouseX = 0, lastMouseY = 0; // scene coords
        private boolean dragging = false;
        private Accordion root;

        public Nature(final Node node) {
            this(node, node);
        }

        public Nature(final Node eventNode, final Node... dragNodes) {
            this.eventNode = eventNode;
            this.dragNodes.addAll(Arrays.asList(dragNodes));
            this.eventNode.addEventHandler(MouseEvent.ANY, this);
        }

        public boolean addDraggedNode(final Node node) {
            if (!this.dragNodes.contains(node)) {
                return this.dragNodes.add(node);
            }
            return false;
        }

        public boolean addListener(final Listener listener) {
            return this.dragListeners.add(listener);
        }

        public void detatch() {
            this.eventNode.removeEventFilter(MouseEvent.ANY, this);
        }

        public List<Node> getDragNodes() {
            return new ArrayList<>(this.dragNodes);
        }

        public Node getEventNode() {
            return this.eventNode;
        }

        @Override
        public void handle(final MouseEvent event) {
            if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
                if (this.enabled && this.eventNode.contains(event.getX(), event.getY())) {
//                    this.lastMouseX = event.getSceneX();
                    this.lastMouseY = event.getSceneY();
                    eventNode.setMouseTransparent(true);
                    event.consume();
                }
            } else if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
                if (!this.dragging) {
                    this.dragging = true;
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Draggable.Event.DragStart);
                    }
                }
                if (this.dragging) {
//                    final double deltaX = event.getSceneX() - this.lastMouseX;
                    final double deltaY = event.getSceneY() - this.lastMouseY;

                    for (final Node dragNode : this.dragNodes) {
                        final double initialTranslateX = dragNode.getTranslateX();
                        final double initialTranslateY = dragNode.getTranslateY();
//                        dragNode.setTranslateX(initialTranslateX + deltaX);
                        dragNode.setTranslateY(initialTranslateY + deltaY);
                    }

//                    this.lastMouseX = event.getSceneX();
                    this.lastMouseY = event.getSceneY();

                    int indexOfDraggingNode = root.getPanes().indexOf(eventNode);
//                    int indexOfDropTarget = root.getPanes().indexOf(event.());

                    System.out.println("indexOfDraggingNode " + indexOfDraggingNode);
//                    System.out.println("indexOfDropTarget " + indexOfDropTarget);

//                    rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);

                    event.consume();
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Draggable.Event.Drag);
                    }
                }
            } else if (MouseEvent.MOUSE_RELEASED == event.getEventType()) {
                if (this.dragging) {
                    System.out.println("get target " + event.getTarget());

                    event.consume();
                    this.dragging = false;
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Draggable.Event.DragEnd);
                    }
                }
            }

        }

        public boolean removeDraggedNode(final Node node) {
            return this.dragNodes.remove(node);
        }

        public boolean removeListener(final Listener listener) {
            return this.dragListeners.remove(listener);
        }

        /**
         * When the initial mousePressed is missing we can supply the first coordinates programmatically.
         *
         * @param lastMouseX
         * @param lastMouseY
         */
        public void setLastMouse(final double lastMouseX, final double lastMouseY) {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
        }

        public void setParentPane(Accordion parent) {
            this.root = parent;
        }

        private void rotateNodes(final Accordion root, final int indexOfDraggingNode,
                                 final int indexOfDropTarget) {
            if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
                final TitledPane node = root.getPanes().remove(indexOfDraggingNode);
                root.getPanes().add(indexOfDropTarget, node);
            }
        }
    }
}

//import javafx.event.EventHandler;
//import javafx.scene.Node;
//import javafx.scene.control.Accordion;
//import javafx.scene.control.TitledPane;
//import javafx.scene.input.MouseDragEvent;
//import javafx.scene.input.MouseEvent;
//import personal.zaytiri.taskerlyze.ui.views.elements.PaneTask;
//
//public class Draggable {
//    DragContext dragContext = new DragContext();
//    Accordion root;
//
//    public Draggable(final Accordion root) {
//        this.root = root;
//    }
//
//    public void addEventToRoot() {
//        root.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
////                removePreview(root);
//                int indexOfDraggingNode = root.getPanes().indexOf(event.getGestureSource());
//                rotateNodes(root, indexOfDraggingNode, root.getPanes().size() - 1);
//            }
//        });
//    }
//
//    public void makeDraggable(final PaneTask titledPane) {
//
//        titledPane.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Node node = ((Node) (event.getSource()));
//                dragContext.y = node.getTranslateY() - event.getSceneY();
//
//                titledPane.setMouseTransparent(true);
//
//                event.setDragDetect(true);
//            }
//        });
////        titledPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
////            @Override
////            public void handle(MouseEvent event) {
////
////            }
////        });
//
//        titledPane.setOnDragDetected(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                titledPane.startFullDrag();
//                System.out.println("setOnDragDetected");
//            }
//        });
//
//        titledPane.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
//                int indexOfDraggingNode = root.getPanes().indexOf(event.getGestureSource());
//                int indexOfDropTarget = root.getPanes().indexOf(titledPane);
//                rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);
//
//            }
//        });
//
//        titledPane.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
//                Node node = ((Node) (event.getGestureSource()));
//                node.setTranslateY(event.getSceneY() + dragContext.y);
//
////                int indexOfDraggingNode = root.getPanes().indexOf(event.getGestureSource());
////                int indexOfDropTarget = root.getPanes().indexOf(titledPane);
////                rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);
//
//
////                System.out.println("indexOfDraggingNode " + indexOfDraggingNode);
////                System.out.println("indexOfDropTarget " + indexOfDropTarget);
//
//                System.out.println("setOnMouseDragOver");
//            }
//        });
//        titledPane.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
////                TitledPane tp = (TitledPane) event.getGestureSource();
////                titledPane.setMouseTransparent(false);
//
//                System.out.println("setOnMouseDragReleased");
//
////                titledPane.setMouseTransparent(false);
//                event.consume();
//            }
//        });
//
//
//    }
//
//    private void rotateNodes(final Accordion root, final int indexOfDraggingNode,
//                             final int indexOfDropTarget) {
//        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
//            final TitledPane node = root.getPanes().remove(indexOfDraggingNode);
//            root.getPanes().add(indexOfDropTarget, node);
//        }
//    }
//
//    class DragContext {
//        double x;
//        double y;
//    }
//}

//import javafx.event.EventHandler;
//        import javafx.scene.Node;
//        import javafx.scene.input.MouseEvent;
//
//public class MouseGestures {
//
//    DragContext dragContext = new DragContext();
//    EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
//
//        Node node = ((Node) (event.getSource()));
//
//        dragContext.x = node.getTranslateX() - event.getSceneX();
//        dragContext.y = node.getTranslateY() - event.getSceneY();
//    };
//    EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
//
//        Node node = ((Node) (event.getSource()));
//
////        node.setTranslateX(dragContext.x + event.getSceneX()); // drag horizontally
//        node.setTranslateY(dragContext.y + event.getSceneY()); // drag vertically
//
//    };
//
//    public void makeDraggable(Node node) {
//        node.setOnMousePressed(onMousePressedEventHandler);
//        node.setOnMouseDragged(onMouseDraggedEventHandler);
//    }
//
//    class DragContext {
//        double x;
//        double y;
//    }
//}